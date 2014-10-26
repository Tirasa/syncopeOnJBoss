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
package org.apache.syncope.console.rest;

import java.util.List;
import javax.annotation.Resource;
import org.apache.syncope.common.mod.UserMod;
import org.apache.syncope.common.search.NodeCond;
import org.apache.syncope.common.services.InvalidSearchConditionException;
import org.apache.syncope.common.services.ResourceService;
import org.apache.syncope.common.services.UserService;
import org.apache.syncope.common.to.BulkAction;
import org.apache.syncope.common.to.BulkActionRes;
import org.apache.syncope.common.to.ConnObjectTO;
import org.apache.syncope.common.to.UserTO;
import org.apache.syncope.common.types.AttributableType;
import org.apache.syncope.common.validation.SyncopeClientCompositeErrorException;
import org.apache.syncope.console.SyncopeSession;
import org.apache.syncope.console.commons.StatusBean;
import org.apache.syncope.console.commons.StatusUtils;
import org.springframework.stereotype.Component;

/**
 * Console client for invoking rest users services.
 * <b>Diverts from released because of create() method - see below</b>.
 */
@Component
public class UserRestClient extends AbstractAttributableRestClient {

    private static final long serialVersionUID = -1575748964398293968L;

    @Resource(name = "baseURL")
    private String baseURL;

    @Override
    public Integer count() {
        return getService(UserService.class).count();
    }

    /**
     * Get all stored users.
     *
     * @param page pagination element to fetch
     * @param size maximum number to fetch
     * @return list of TaskTO objects
     */
    @Override
    public List<UserTO> list(final int page, final int size) {
        return getService(UserService.class).list(page, size);
    }

    /**
     * Makes explicit usage of Spring's restTemplate to avoid JAX-RS Response not compatible with the
     * JAX-RS version available with JBoss 7 and 7.1.
     */
    public UserTO create(final UserTO userTO) {
        return SyncopeSession.get().getRestTemplate().postForObject(baseURL + "user/create", userTO, UserTO.class);
    }

    public UserTO update(final UserMod userModTO) {
        return getService(UserService.class).update(userModTO.getId(), userModTO);
    }

    @Override
    public UserTO delete(final Long id) {
        return getService(UserService.class).delete(id);
    }

    public UserTO read(final Long id) {
        UserTO userTO = null;
        try {
            userTO = getService(UserService.class).read(id);
        } catch (SyncopeClientCompositeErrorException e) {
            LOG.error("While reading a user", e);
        }
        return userTO;
    }

    public UserTO read(final String username) {
        UserTO userTO = null;
        try {
            userTO = getService(UserService.class).read(username);
        } catch (SyncopeClientCompositeErrorException e) {
            LOG.error("While reading a user", e);
        }
        return userTO;
    }

    public UserTO readProfile() {
        return getService(UserService.class).readSelf();
    }

    @Override
    public Integer searchCount(final NodeCond searchCond) throws InvalidSearchConditionException {
        return getService(UserService.class).searchCount(searchCond);
    }

    @Override
    public List<UserTO> search(final NodeCond searchCond, final int page, final int size)
            throws InvalidSearchConditionException {

        return getService(UserService.class).search(searchCond, page, size);
    }

    @Override
    public ConnObjectTO getConnectorObject(final String resourceName, final Long id) {
        return getService(ResourceService.class).getConnectorObject(resourceName, AttributableType.USER, id);
    }

    public UserTO suspend(final long userId, final List<StatusBean> statuses) {
        return getService(UserService.class).suspend(userId, StatusUtils.buildPropagationRequestTO(statuses, false));
    }

    public UserTO reactivate(final long userId, final List<StatusBean> statuses) {
        return getService(UserService.class).reactivate(userId, StatusUtils.buildPropagationRequestTO(statuses, true));
    }

    @Override
    public BulkActionRes bulkAction(final BulkAction action) {
        return getService(UserService.class).bulkAction(action);
    }
}
