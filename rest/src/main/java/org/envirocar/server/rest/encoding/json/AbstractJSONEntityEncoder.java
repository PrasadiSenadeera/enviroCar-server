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
package org.envirocar.server.rest.encoding.json;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.google.inject.Inject;
import org.envirocar.server.rest.encoding.JSONEntityEncoder;
import org.joda.time.format.DateTimeFormatter;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public abstract class AbstractJSONEntityEncoder<T>
        extends AbstractJSONMessageBodyWriter<T>
        implements JSONEntityEncoder<T> {
    private JsonNodeFactory jsonFactory;
    private DateTimeFormatter dateTimeFormat;

    public AbstractJSONEntityEncoder(Class<T> classType) {
        super(classType);
    }

    public JsonNodeFactory getJsonFactory() {
        return jsonFactory;
    }

    @Inject
    public void setJsonFactory(JsonNodeFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    public DateTimeFormatter getDateTimeFormat() {
        return dateTimeFormat;
    }

    @Inject
    public void setDateTimeFormat(DateTimeFormatter dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

}