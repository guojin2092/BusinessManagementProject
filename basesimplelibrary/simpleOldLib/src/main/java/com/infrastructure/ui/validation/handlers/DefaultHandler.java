/**
 * Copyright (C) 2015 JianyingLi <lijy91@foxmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.infrastructure.ui.validation.handlers;

import android.view.View;
import android.widget.TextView;

import com.infrastructure.ui.validation.ErrorHandler;
import com.infrastructure.ui.validation.Rule;
import com.infrastructure.ui.validation.ValidationError;

import java.util.List;

public class DefaultHandler implements ErrorHandler {


    @Override
    public void onValid(List<Rule> rules) {
        for (Rule rule : rules) {
            ((TextView) rule.view()).setError(null, null);
        }
    }

    @Override
    public void onInValid(List<Rule> rules, List<ValidationError> errors) {
        for (Rule rule : rules) {
            ((TextView) rule.view()).setError(null, null);
        }
        for (ValidationError error : errors) {
            View view = error.view();
            StringBuilder sb = new StringBuilder();
            for (String key : error.errorMessages().keySet()) {
                if (!"".equals(sb.toString())) {
                    sb.append("\n");
                }
                sb.append(error.errorMessages().get(key));
            }
            if (view instanceof TextView) {
                ((TextView) view).setError(sb.toString());
            }
        }
    }

}
