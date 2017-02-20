/*
 * Copyright (C) 2013-2017 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.terasoluna.tourreservation.app.searchtour;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.tourreservation.app.searchtour.SearchTourForm;

@Component
public class SearchTourFormDateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (SearchTourForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchTourForm searchTourForm = (SearchTourForm) target;

        try {
            new DateTime(searchTourForm.getDepYear(), searchTourForm
                    .getDepMonth(), searchTourForm.getDepDay(), 0, 0).toDate();
        } catch (IllegalFieldValueException e) {
            errors.rejectValue("depYear", "IncorrectDate.inputdate",
                    "Incorrect date was entered.");
        }

    }
}
