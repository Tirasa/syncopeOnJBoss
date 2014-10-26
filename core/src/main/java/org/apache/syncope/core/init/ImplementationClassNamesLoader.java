/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.core.init;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.syncope.core.persistence.validation.attrvalue.AlwaysTrueValidator;
import org.apache.syncope.core.persistence.validation.attrvalue.BasicValidator;
import org.apache.syncope.core.persistence.validation.attrvalue.EmailAddressValidator;
import org.apache.syncope.core.propagation.DefaultPropagationActions;
import org.apache.syncope.core.propagation.impl.LDAPMembershipPropagationActions;
import org.apache.syncope.core.quartz.SampleJob;
import org.apache.syncope.core.report.RoleReportlet;
import org.apache.syncope.core.report.StaticReportlet;
import org.apache.syncope.core.report.UserReportlet;
import org.apache.syncope.core.sync.DefaultSyncActions;
import org.apache.syncope.core.sync.impl.LDAPMembershipSyncActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Overrides default classloader-scanning behavior with static class names: for usage with JBoss.
 */
@Component
public class ImplementationClassNamesLoader {

    public enum Type {

        REPORTLET,
        TASKJOB,
        SYNC_ACTIONS,
        SYNC_CORRELATION_RULES,
        PROPAGATION_ACTIONS,
        VALIDATOR

    }

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ImplementationClassNamesLoader.class);

    private Map<Type, Set<String>> classNames;

    public void load() {
        classNames = new EnumMap<Type, Set<String>>(Type.class);
        for (Type type : Type.values()) {
            classNames.put(type, new HashSet<String>());
        }

        Set<String> classes = new HashSet<String>();
        classes.add(UserReportlet.class.getName());
        classes.add(RoleReportlet.class.getName());
        classes.add(StaticReportlet.class.getName());
        classNames.put(Type.REPORTLET, classes);

        classes = new HashSet<String>();
        classes.add(SampleJob.class.getName());
        classNames.put(Type.TASKJOB, classes);

        classes = new HashSet<String>();
        classes.add(DefaultSyncActions.class.getName());
        classes.add(LDAPMembershipSyncActions.class.getName());
        classNames.put(Type.SYNC_ACTIONS, classes);

        classes = new HashSet<String>();
        classes.add(DefaultPropagationActions.class.getName());
        classes.add(LDAPMembershipPropagationActions.class.getName());
        classNames.put(Type.PROPAGATION_ACTIONS, classes);

        classes = new HashSet<String>();
        classes.add(BasicValidator.class.getName());
        classes.add(AlwaysTrueValidator.class.getName());
        classes.add(EmailAddressValidator.class.getName());
        classNames.put(Type.VALIDATOR, classes);

        classNames = Collections.unmodifiableMap(classNames);

        LOG.debug("Implementation classes found: {}", classNames);
    }

    public Set<String> getClassNames(final Type type) {
        return classNames.get(type);
    }
}
