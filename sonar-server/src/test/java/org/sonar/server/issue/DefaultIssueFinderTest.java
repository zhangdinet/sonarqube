/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2013 SonarSource
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
package org.sonar.server.issue;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.sonar.api.component.Component;
import org.sonar.api.issue.ActionPlan;
import org.sonar.api.issue.Issue;
import org.sonar.api.issue.IssueQuery;
import org.sonar.api.issue.IssueQueryResult;
import org.sonar.api.rules.Rule;
import org.sonar.api.user.UserFinder;
import org.sonar.api.web.UserRole;
import org.sonar.core.component.ComponentDto;
import org.sonar.core.issue.DefaultActionPlan;
import org.sonar.core.issue.db.IssueChangeDao;
import org.sonar.core.issue.db.IssueDao;
import org.sonar.core.issue.db.IssueDto;
import org.sonar.core.persistence.MyBatis;
import org.sonar.core.resource.ResourceDao;
import org.sonar.core.rule.DefaultRuleFinder;
import org.sonar.core.user.AuthorizationDao;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.*;

public class DefaultIssueFinderTest {

  MyBatis mybatis = mock(MyBatis.class);
  IssueDao issueDao = mock(IssueDao.class);
  IssueChangeDao issueChangeDao = mock(IssueChangeDao.class);
  AuthorizationDao authorizationDao = mock(AuthorizationDao.class);
  DefaultRuleFinder ruleFinder = mock(DefaultRuleFinder.class);
  ResourceDao resourceDao = mock(ResourceDao.class);
  ActionPlanService actionPlanService = mock(ActionPlanService.class);
  UserFinder userFinder = mock(UserFinder.class);
  DefaultIssueFinder finder = new DefaultIssueFinder(mybatis, issueDao, issueChangeDao, authorizationDao, ruleFinder, userFinder, resourceDao, actionPlanService);

  @Test
  public void should_find_issues() {
    when(authorizationDao.selectAuthorizedRootProjectsIds(anyInt(), anyString(), any(SqlSession.class)))
      .thenReturn(newHashSet(100));
    IssueQuery query = IssueQuery.builder().build();

    IssueDto issue1 = new IssueDto().setId(1L).setRuleId(50).setResourceId(123).setProjectId(100)
      .setComponentKey_unit_test_only("Action.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    IssueDto issue2 = new IssueDto().setId(2L).setRuleId(50).setResourceId(123).setProjectId(100)
      .setComponentKey_unit_test_only("Action.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    List<IssueDto> dtoList = newArrayList(issue1, issue2);
    when(issueDao.selectByIds(anyCollection(), any(IssueQuery.Sort.class), anyBoolean(), any(SqlSession.class))).thenReturn(dtoList);

    IssueQueryResult results = finder.find(query);
    verify(issueDao).selectIssueAndProjectIds(eq(query), eq(newHashSet(100)), any(SqlSession.class));

    assertThat(results.issues()).hasSize(2);
    Issue issue = results.issues().iterator().next();
    assertThat(issue.componentKey()).isEqualTo("Action.java");
    assertThat(issue.projectKey()).isEqualTo("struts");
    assertThat(issue.ruleKey().toString()).isEqualTo("squid:AvoidCycle");
    assertThat(results.securityExclusions()).isFalse();
  }

  @Test
  public void should_find_only_authorized_issues() {
    when(authorizationDao.selectAuthorizedRootProjectsIds(anyInt(), anyString(), any(SqlSession.class)))
      .thenReturn(Collections.<Integer>emptySet());
    IssueQuery query = IssueQuery.builder().pageSize(100).requiredRole(UserRole.USER).build();

    IssueDto issue1 = new IssueDto().setId(1L).setRuleId(50).setResourceId(123).setProjectId(100)
      .setComponentKey_unit_test_only("Action.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    when(issueDao.selectByIds(anyCollection(), any(IssueQuery.Sort.class), anyBoolean(), any(SqlSession.class))).thenReturn(newArrayList(issue1));

    finder.find(query);
    verify(issueDao).selectIssueAndProjectIds(eq(query), eq(Collections.<Integer>emptySet()), any(SqlSession.class));

    // TODO
//    assertThat(results.securityExclusions()).isTrue();
  }

  @Test
  public void should_find_paginate_result() {
    when(authorizationDao.selectAuthorizedRootProjectsIds(anyInt(), anyString(), any(SqlSession.class)))
      .thenReturn(newHashSet(100));

    IssueQuery query = IssueQuery.builder().pageSize(1).pageIndex(1).build();

    IssueDto issue1 = new IssueDto().setId(1L).setRuleId(50).setResourceId(123).setProjectId(100)
      .setComponentKey_unit_test_only("Action.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    IssueDto issue2 = new IssueDto().setId(2L).setRuleId(50).setResourceId(135).setProjectId(100)
      .setComponentKey_unit_test_only("Phases.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    List<IssueDto> dtoList = newArrayList(issue1, issue2);
    when(issueDao.selectIssueAndProjectIds(eq(query), eq(newHashSet(100)), any(SqlSession.class))).thenReturn(dtoList);
    when(issueDao.selectByIds(anyCollection(), any(IssueQuery.Sort.class), anyBoolean(), any(SqlSession.class))).thenReturn(dtoList);

    IssueQueryResult results = finder.find(query);
    assertThat(results.paging().offset()).isEqualTo(0);
    assertThat(results.paging().total()).isEqualTo(2);
    assertThat(results.paging().pages()).isEqualTo(2);

    // Only one result is expected because the limit is 1
    verify(issueDao).selectByIds(eq(newHashSet(1L)), any(IssueQuery.Sort.class), anyBoolean(), any(SqlSession.class));
  }

  @Test
  public void should_find_by_key() {
    IssueDto issueDto = new IssueDto().setId(1L).setRuleId(1).setResourceId(1).setProjectId(100)
      .setComponentKey_unit_test_only("Action.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    when(issueDao.selectByKey("ABCDE")).thenReturn(issueDto);

    Issue issue = finder.findByKey("ABCDE");
    assertThat(issue).isNotNull();
    assertThat(issue.componentKey()).isEqualTo("Action.java");
    assertThat(issue.ruleKey().toString()).isEqualTo("squid:AvoidCycle");
  }

  @Test
  public void should_get_rule_from_result() {
    Rule rule = Rule.create().setRepositoryKey("squid").setKey("AvoidCycle");
    when(ruleFinder.findByIds(anyCollection())).thenReturn(newArrayList(rule));

    IssueQuery query = IssueQuery.builder().build();

    IssueDto issue1 = new IssueDto().setId(1L).setRuleId(50).setResourceId(123).setProjectId(100)
      .setComponentKey_unit_test_only("Action.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    IssueDto issue2 = new IssueDto().setId(2L).setRuleId(50).setResourceId(123).setProjectId(100)
      .setComponentKey_unit_test_only("Action.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    List<IssueDto> dtoList = newArrayList(issue1, issue2);
    when(issueDao.selectByIds(anyCollection(), any(IssueQuery.Sort.class), anyBoolean(), any(SqlSession.class))).thenReturn(dtoList);

    IssueQueryResult results = finder.find(query);
    assertThat(results.issues()).hasSize(2);
    Issue issue = results.issues().iterator().next();
    assertThat(results.issues()).hasSize(2);
    assertThat(results.rule(issue)).isEqualTo(rule);
    assertThat(results.rules()).hasSize(1);
  }

  @Test
  public void should_get_component_from_result() {
    Component component = new ComponentDto().setKey("Action.java");
    when(resourceDao.findByIds(anyCollection())).thenReturn(newArrayList(component));

    IssueQuery query = IssueQuery.builder().build();

    IssueDto issue1 = new IssueDto().setId(1L).setRuleId(50).setResourceId(123).setProjectId(100)
      .setComponentKey_unit_test_only("Action.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    IssueDto issue2 = new IssueDto().setId(2L).setRuleId(50).setResourceId(123).setProjectId(100)
      .setComponentKey_unit_test_only("Action.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    List<IssueDto> dtoList = newArrayList(issue1, issue2);
    when(issueDao.selectByIds(anyCollection(), any(IssueQuery.Sort.class), anyBoolean(), any(SqlSession.class))).thenReturn(dtoList);

    IssueQueryResult results = finder.find(query);
    assertThat(results.issues()).hasSize(2);
    assertThat(results.components()).hasSize(1);
    Issue issue = results.issues().iterator().next();
    assertThat(results.component(issue)).isEqualTo(component);
  }

  @Test
  public void should_get_project_from_result() {
    Component project = new ComponentDto().setKey("struts");
    when(resourceDao.findByIds(anyCollection())).thenReturn(newArrayList(project));

    IssueQuery query = IssueQuery.builder().build();

    IssueDto issue1 = new IssueDto().setId(1L).setRuleId(50).setResourceId(123).setProjectId(100)
                        .setComponentKey_unit_test_only("Action.java")
                        .setProjectKey_unit_test_only("struts")
                        .setRuleKey_unit_test_only("squid", "AvoidCycle")
                        .setStatus("OPEN").setResolution("OPEN");
    IssueDto issue2 = new IssueDto().setId(2L).setRuleId(50).setResourceId(123).setProjectId(100)
                        .setComponentKey_unit_test_only("Action.java")
                        .setProjectKey_unit_test_only("struts")
                        .setRuleKey_unit_test_only("squid", "AvoidCycle")
                        .setStatus("OPEN").setResolution("OPEN");
    List<IssueDto> dtoList = newArrayList(issue1, issue2);
    when(issueDao.selectByIds(anyCollection(), any(IssueQuery.Sort.class), anyBoolean(), any(SqlSession.class))).thenReturn(dtoList);

    IssueQueryResult results = finder.find(query);
    assertThat(results.issues()).hasSize(2);
    assertThat(results.projects()).hasSize(1);
    Issue issue = results.issues().iterator().next();
    assertThat(results.project(issue)).isEqualTo(project);
  }

  @Test
  public void should_get_action_plans_from_result() {
    ActionPlan actionPlan1 = DefaultActionPlan.create("Short term").setKey("A");
    ActionPlan actionPlan2 = DefaultActionPlan.create("Long term").setKey("B");

    IssueQuery query = IssueQuery.builder().build();

    IssueDto issue1 = new IssueDto().setId(1L).setRuleId(50).setResourceId(123).setProjectId(100).setKee("ABC").setActionPlanKey("A")
      .setComponentKey_unit_test_only("Action.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    IssueDto issue2 = new IssueDto().setId(2L).setRuleId(50).setResourceId(123).setProjectId(100).setKee("DEF").setActionPlanKey("B")
      .setComponentKey_unit_test_only("Action.java")
      .setProjectKey_unit_test_only("struts")
      .setRuleKey_unit_test_only("squid", "AvoidCycle")
      .setStatus("OPEN").setResolution("OPEN");
    List<IssueDto> dtoList = newArrayList(issue1, issue2);
    when(issueDao.selectByIds(anyCollection(), any(IssueQuery.Sort.class), anyBoolean(), any(SqlSession.class))).thenReturn(dtoList);
    when(actionPlanService.findByKeys(anyCollection())).thenReturn(newArrayList(actionPlan1, actionPlan2));

    IssueQueryResult results = finder.find(query);
    assertThat(results.issues()).hasSize(2);
    assertThat(results.actionPlans()).hasSize(2);
    Issue issue = results.issues().iterator().next();
    assertThat(results.actionPlan(issue)).isNotNull();
  }

  @Test
  public void should_get_empty_result_when_no_issue() {
    grantAccessRights();
    IssueQuery query = IssueQuery.builder().build();
    when(issueDao.selectIssueAndProjectIds(eq(query), anyCollection(), any(SqlSession.class))).thenReturn(Collections.<IssueDto>emptyList());
    when(issueDao.selectByIds(anyCollection(), any(IssueQuery.Sort.class), anyBoolean(), any(SqlSession.class))).thenReturn(Collections.<IssueDto>emptyList());

    IssueQueryResult results = finder.find(query);
    assertThat(results.issues()).isEmpty();
    assertThat(results.rules()).isEmpty();
    assertThat(results.components()).isEmpty();
    assertThat(results.actionPlans()).isEmpty();
  }

  private void grantAccessRights() {
    when(authorizationDao.selectAuthorizedRootProjectsIds(anyInt(), anyString(), any(SqlSession.class)))
      .thenReturn(newHashSet(100));
  }
}