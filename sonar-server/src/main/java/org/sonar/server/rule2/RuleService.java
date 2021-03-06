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

import org.sonar.api.ServerComponent;
import org.sonar.api.rule.RuleKey;
import org.sonar.core.rule.RuleDao;
import org.sonar.server.search.Hit;
import org.sonar.server.search.QueryOptions;
import org.sonar.server.search.Results;

import javax.annotation.CheckForNull;
import java.util.List;

/**
 * @since 4.4
 */
public class RuleService implements ServerComponent {

  private RuleDao dao;
  private RuleIndex index;

  public RuleService(RuleDao dao, RuleIndex index) {
    this.dao = dao;
    this.index = index;
  }

  @CheckForNull
  public Rule getByKey(RuleKey key) {
    Hit hit = index.getByKey(key);
    if (hit != null) {
      return new RuleDoc(hit);
    }
    return null;
  }

  public RuleQuery newRuleQuery() {
    return new RuleQuery();
  }

  public Results search(RuleQuery query, QueryOptions options) {
    // keep only supported fields and add the fields to always return
    options.filterFieldsToReturn(RuleIndex.PUBLIC_FIELDS);
    options.addFieldsToReturn(RuleNormalizer.RuleField.REPOSITORY.key(), RuleNormalizer.RuleField.KEY.key());
    return index.search(query, options);
  }

  /**
   * List all tags
   */
  public List<String> listTags() {
    throw new UnsupportedOperationException("TODO");
  }

  public RuleService refresh(){
    this.index.refresh();
    return this;
  }
}
