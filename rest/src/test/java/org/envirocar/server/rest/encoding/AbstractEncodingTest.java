/*
 * Copyright (C) 2013-2018 The enviroCar project
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
package org.envirocar.server.rest.encoding;

import com.google.inject.Inject;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.envirocar.server.core.DataService;
import org.envirocar.server.core.dao.UserDao;
import org.envirocar.server.core.entities.EntityFactory;
import org.envirocar.server.rest.encoding.csv.TrackCSVEncoder;
import org.envirocar.server.rest.encoding.shapefile.TrackShapefileEncoder;
import org.envirocar.server.rest.schema.GuiceRunner;
import org.envirocar.server.rest.schema.Modules;
import org.junit.runner.RunWith;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;


public abstract class AbstractEncodingTest {

    @Inject
    protected DataService dataService;

    @Inject
    protected UserDao userDao;

    @Inject
    protected TrackCSVEncoder trackCSVEncoder;

    @Inject
    protected TrackShapefileEncoder trackShapefileEncoder;

    @Inject
    protected EntityFactory entityFactory;



    public AbstractEncodingTest() {
        MorphiaLoggerFactory.reset();
    }

}
