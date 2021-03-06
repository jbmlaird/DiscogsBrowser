/*
 *   Copyright 2016 Fabio Collini.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package bj.vinylbrowser.testutils;

import android.support.test.InstrumentationRegistry;

import bj.vinylbrowser.App;
import bj.vinylbrowser.AppComponent;
import bj.vinylbrowser.AppModule;
import bj.vinylbrowser.greendao.DaoModule;
import bj.vinylbrowser.utils.UtilsModule;
import it.cosenonjaviste.daggermock.DaggerMockRule;

public class EspressoDaggerMockRule extends DaggerMockRule<AppComponent>
{
    public EspressoDaggerMockRule()
    {
        super(AppComponent.class, new AppModule(getApp()), new UtilsModule(), new DaoModule());
        set(component -> getApp().setComponent(component));
    }

    public static App getApp()
    {
        return (App) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }
}
