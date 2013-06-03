/*
 * Copyright (C) 2013  Christian Autermann, Jan Alexander Wirwahn,
 *                     Arne De Wall, Dustin Demuth, Saqib Rasheed
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.car.server.mongo.dao;

import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.Datastore;
import com.google.inject.Inject;

import io.car.server.core.activities.Activities;
import io.car.server.core.activities.Activity;
import io.car.server.core.activities.ActivityType;
import io.car.server.core.dao.ActivityDao;
import io.car.server.core.entities.User;
import io.car.server.core.util.Pagination;
import io.car.server.mongo.activities.MongoActivity;

/**
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public class MongoActivityDao extends AbstractMongoDao<ObjectId, MongoActivity, Activities>
        implements ActivityDao {
    @Inject
    public MongoActivityDao(Datastore ds) {
        super(MongoActivity.class, ds);
    }

    @Override
    protected Activities createPaginatedIterable(Iterable<MongoActivity> i,
                                                 Pagination p, long count) {
        return Activities.from(i).withPagination(p).withElements(count).build();
    }

    @Override
    public void save(Activity activity) {
        save((MongoActivity) activity);
    }

    @Override
    public Activities get(Pagination p) {
        return fetch(q(), p);
    }

    @Override
    public Activities get(User user, Pagination p) {
        return fetch(q().field(MongoActivity.USER).equal(user), p);
    }

    @Override
    public Activities get(ActivityType type, Pagination p) {
        return fetch(q().field(MongoActivity.TYPE).equal(type), p);
    }

    @Override
    public Activities get(ActivityType type, User user, Pagination p) {
        return fetch(q()
                .field(MongoActivity.USER).equal(user)
                .field(MongoActivity.TYPE).equal(type), p);
    }
}
