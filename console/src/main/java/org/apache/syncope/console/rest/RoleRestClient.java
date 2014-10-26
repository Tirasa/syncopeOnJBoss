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

import org.apache.syncope.common.mod.RoleMod;
import org.apache.syncope.common.search.NodeCond;
import org.apache.syncope.common.services.InvalidSearchConditionException;
import org.apache.syncope.common.services.ResourceService;
import org.apache.syncope.common.services.RoleService;
import org.apache.syncope.common.to.BulkAction;
import org.apache.syncope.common.to.BulkActionRes;
import org.apache.syncope.common.to.ConnObjectTO;
import org.apache.syncope.common.to.RoleTO;
import org.apache.syncope.common.types.AttributableType;
import org.apache.syncope.console.SyncopeSession;
import org.springframework.stereotype.Component;

/**
 * Console client for invoking Rest Role's services.
 * <b>Diverts from released because of create() method - see below</b>.
 */
@Component
public class RoleRestClient extends AbstractAttributableRestClient {

    private static final long serialVersionUID = -8549081557283519638L;

    @Resource(name = "baseURL")
    private String baseURL;

    @Override
    public Integer count() {
        return getService(RoleService.class).count();
    }

    public List<RoleTO> list() {
        return getService(RoleService.class).list();
    }

    @Override
    public List<RoleTO> list(final int page, final int size) {
        return getService(RoleService.class).list(page, size);
    }

    @Override
    public Integer searchCount(final NodeCond searchCond) throws InvalidSearchConditionException {
        return getService(RoleService.class).searchCount(searchCond);
    }

    @Override
    public List<RoleTO> search(final NodeCond searchCond, final int page, final int size)
            throws InvalidSearchConditionException {

        return getService(RoleService.class).search(searchCond, page, size);
    }

    @Override
    public ConnObjectTO getConnectorObject(final String resourceName, final Long id) {
        return getService(ResourceService.class).getConnectorObject(resourceName, AttributableType.ROLE, id);
    }

    /**
     * Makes explicit usage of Spring's restTemplate to avoid JAX-RS Response not compatible with the
     * JAX-RS version available with JBoss 7 and 7.1.
     */
    public RoleTO create(final RoleTO roleTO) {
        return SyncopeSession.get().getRestTemplate().postForObject(baseURL + "role/create", roleTO, RoleTO.class);
    }

    public RoleTO read(final Long id) {
        return getService(RoleService.class).read(id);
    }

    public RoleTO update(final RoleMod roleMod) {
        return getService(RoleService.class).update(roleMod.getId(), roleMod);
    }

    @Override
    public RoleTO delete(final Long id) {
        return getService(RoleService.class).delete(id);
    }

    @Override
    public BulkActionRes bulkAction(final BulkAction action) {
        return getService(RoleRestClient.class).bulkAction(action);
    }
}
