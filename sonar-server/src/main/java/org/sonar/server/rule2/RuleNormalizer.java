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

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Cardinality;
import org.sonar.core.rule.RuleDto;
import org.sonar.core.rule.RuleParamDto;
import org.sonar.core.rule.RuleRuleTagDto;
import org.sonar.core.rule.RuleTagType;
import org.sonar.server.search.BaseNormalizer;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class RuleNormalizer extends BaseNormalizer<RuleDto, RuleKey> {

  private final RuleDao ruleDao;

  public static enum RuleField {
    KEY("key"),
    REPOSITORY("repo"),
    NAME("name"),
    CREATED_AT("createdAt"),
    HTML_DESCRIPTION("htmlDesc"),
    SEVERITY("severity"),
    STATUS("status"),
    LANGUAGE("lang"),
    TAGS("tags"),
    SYSTEM_TAGS("sysTags"),
    INTERNAL_KEY("internalKey"),
    TEMPLATE("template"),
    UPDATED_AT("updatedAt"),
    PARAMS("params"),
    ACTIVE("active");

    private final String key;

    private RuleField(final String key) {
      this.key = key;
    }

    public String key() {
      return key;
    }

    @Override
    public String toString() {
      return key;
    }
  }

  public static enum RuleParamField {
    NAME("name"),
    TYPE("type"),
    DESCRIPTION("description"),
    DEFAULT_VALUE("defaultValue");

    private final String key;

    private RuleParamField(final String key) {
      this.key = key;
    }

    public String key() {
      return key;
    }

    @Override
    public String toString() {
      return key;
    }
  }

  public RuleNormalizer(RuleDao ruleDao){
    this.ruleDao = ruleDao;
  }

  @Override
  public UpdateRequest normalize(RuleKey key) throws IOException {
    return normalize(ruleDao.getByKey(key));
  }

  @Override
  public UpdateRequest normalize(RuleDto rule) throws IOException {
    XContentBuilder document = jsonBuilder().startObject();
    indexField(RuleField.KEY.key(), rule.getRuleKey(), document);
    indexField(RuleField.REPOSITORY.key(), rule.getRepositoryKey(), document);
    indexField(RuleField.NAME.key(), rule.getName(), document);
    indexField(RuleField.CREATED_AT.key(), rule.getCreatedAt(), document);
    indexField(RuleField.UPDATED_AT.key(), rule.getUpdatedAt(), document);
    indexField(RuleField.HTML_DESCRIPTION.key(), rule.getDescription(), document);
    indexField(RuleField.SEVERITY.key(), rule.getSeverityString(), document);
    indexField(RuleField.STATUS.key(), rule.getStatus(), document);
    indexField(RuleField.LANGUAGE.key(), rule.getLanguage(), document);
    indexField(RuleField.INTERNAL_KEY.key(), rule.getConfigKey(), document);
    indexField(RuleField.TEMPLATE.key(), rule.getCardinality() == Cardinality.MULTIPLE, document);

    document.startArray(RuleField.TAGS.key()).endArray();
    document.startArray(RuleField.SYSTEM_TAGS.key()).endArray();
    document.startObject(RuleField.PARAMS.key()).endObject();
    document.startObject(RuleField.ACTIVE.key()).endObject();

    /* Done normalizing for Rule */
    document.endObject();

    /* Creating updateRequest */
    UpdateRequest request =  new UpdateRequest().doc(document);
    request.docAsUpsert(true);
    return request;
  }

  public UpdateRequest normalize(RuleParamDto param, RuleKey key) throws IOException {
     /* Normalize the params */
    XContentBuilder document = jsonBuilder().startObject();
    document.startObject(RuleField.PARAMS.key());
    document.startObject(param.getName());
    indexField(RuleParamField.NAME.key(), param.getName(), document);
    indexField(RuleParamField.TYPE.key(), param.getType(), document);
    indexField(RuleParamField.DESCRIPTION.key(), param.getDescription(), document);
    indexField(RuleParamField.DEFAULT_VALUE.key(), param.getDefaultValue(), document);
    document.endObject();
    document.endObject();

    /* Creating updateRequest */
    UpdateRequest request =  new UpdateRequest().doc(document);
    request.docAsUpsert(true);
    return request;

  }

  public UpdateRequest normalize(RuleRuleTagDto tag, RuleKey key) throws IOException {
    String field = RuleField.TAGS.key();
    if(tag.getType().equals(RuleTagType.SYSTEM)){
      field = RuleField.SYSTEM_TAGS.key();
    }
    return new UpdateRequest()
      .script("ctx._source."+field+" += tag")
      .addScriptParam("tag",tag.getTag());
  }
}
