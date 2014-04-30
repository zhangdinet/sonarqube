/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2014 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.rule2;

import com.google.common.collect.ImmutableSet;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.core.cluster.WorkQueue;
import org.sonar.core.profiling.Profiling;
import org.sonar.core.rule.RuleConstants;
import org.sonar.core.rule.RuleDto;
import org.sonar.server.es.ESNode;
import org.sonar.server.rule2.RuleNormalizer.RuleField;
import org.sonar.server.search.BaseIndex;
import org.sonar.server.search.Hit;
import org.sonar.server.search.QueryOptions;
import org.sonar.server.search.Results;

import java.io.IOException;
import java.util.Set;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class RuleIndex extends BaseIndex<RuleKey, RuleDto> {

  private static final Logger LOG = LoggerFactory.getLogger(RuleIndex.class);

  public static final Set<String> PUBLIC_FIELDS = ImmutableSet.of(
    RuleField.KEY.key(),
    RuleField.NAME.key(),
    RuleField.DESCRIPTION.key(),
    RuleField.LANGUAGE.key(),
    RuleField.SEVERITY.key(),
    RuleField.STATUS.key(),
    RuleField.TAGS.key(),
    RuleField.SYSTEM_TAGS.key(),
    RuleField.CREATED_AT.key(),
    RuleField.UDPATED_AT.key());

  public RuleIndex(RuleNormalizer normalizer, WorkQueue workQueue,
    Profiling profiling, ESNode node) {
    super(normalizer, workQueue, profiling, node);
  }

  @Override
  public String getIndexName() {
    return RuleConstants.INDEX_NAME;
  }

  @Override
  protected String getType() {
    return RuleConstants.ES_TYPE;
  }

  protected String getKeyValue(RuleKey key) {
    return key.toString();
  }

  @Override
  protected XContentBuilder getIndexSettings() {
    try {
      return jsonBuilder().startObject()
        .startObject("index")
        .field("number_of_replicas", 0)
        .field("number_of_shards", 3)
        .startObject("mapper")
        .field("dynamic", true)
        .endObject()
        .startObject("analysis")
        .startObject("analyzer")
        .startObject("path_analyzer")
        .field("type", "custom")
        .field("tokenizer", "path_hierarchy")
        .endObject()
        .endObject()
        .endObject()
        .endObject().endObject();
    } catch (IOException e) {
      LOG.error("Could not create index settings for {}", this.getIndexName());
      return null;
    }
  }

  private void addMatchField(XContentBuilder mapping, String field, String type) throws IOException {
    mapping.startObject(field)
      .field("type", type)
      .field("index", "not_analyzed")
      .endObject();
  }

  @Override
  protected XContentBuilder getMapping() {
    try {
      XContentBuilder mapping = jsonBuilder().startObject()
        .startObject(this.getType())
        .field("dynamic", true)
        .startObject("properties");

      addMatchField(mapping, RuleField.KEY.key(), "string");
      addMatchField(mapping, RuleField.REPOSITORY.key(), "string");
      addMatchField(mapping, RuleField.SEVERITY.key(), "string");

      mapping.startObject("active")
        .field("type", "nested")
        .field("dynamic", true)
        .endObject();

      return mapping.endObject()
        .endObject().endObject();

    } catch (IOException e) {
      LOG.error("Could not create mapping for {}", this.getIndexName());
      return null;
    }
  }

  public Results search(RuleQuery query, QueryOptions options) {

    // Build main query (search based)
    QueryBuilder qb;
    if (query.getQueryText() != null && !query.getQueryText().isEmpty()) {
      qb = QueryBuilders.multiMatchQuery(query.getQueryText(),
        RuleField.NAME.key(),
        RuleField.DESCRIPTION.key(),
        RuleField.KEY.key(),
        RuleField.LANGUAGE.key(),
        RuleField.TAGS.key());
    } else {
      qb = QueryBuilders.matchAllQuery();
    }

    // Build main filter (match based)
    BoolFilterBuilder fb = FilterBuilders.boolFilter();

    this.addTermFilter(RuleField.LANGUAGE.key(), query.getLanguages(), fb);
    this.addTermFilter(RuleField.REPOSITORY.key(), query.getRepositories(), fb);
    this.addTermFilter(RuleField.SEVERITY.key(), query.getSeverities(), fb);
    this.addTermFilter(RuleField.KEY.key(), query.getKey(), fb);

    //Create ES query Object;
    SearchResponse esResult = getClient()
      .prepareSearch(this.getIndexName())
      .setQuery(QueryBuilders.filteredQuery(qb, fb))
      .addFields(options.getFieldsToReturn().toArray(new String[options.getFieldsToReturn().size()]))
      .get();

    Results results = new Results()
      .setTotal((int) esResult.getHits().totalHits())
      .setTime(esResult.getTookInMillis());

    for (SearchHit hit : esResult.getHits().getHits()) {
      results.getHits().add(
        Hit.fromMap(hit.score(), hit.sourceAsMap()));
    }

    return results;
  }
}