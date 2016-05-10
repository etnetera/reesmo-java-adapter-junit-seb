/* Copyright 2016 Etnetera a.s.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.etnetera.reesmo.adapter.junit.seb;

import java.util.logging.Level;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import cz.etnetera.seb.Seb;

/**
 * JUnit test watcher which tracks results from Seb.
 */
public class ReesmoSebJUnitWatcher extends TestWatcher {

	public static final String FAILED_REPORT = "FAILED";
	
	protected Seb seb;

	public void setSeb(Seb seb) {
		this.seb = seb;
	}
	
	@Override
	protected void succeeded(Description description) {
		if (seb != null)
			seb.log(Level.INFO, "Seb succeeded " + seb.getLabel());
	}

	@Override
	protected void failed(Throwable e, Description description) {
		if (seb != null) {
        	seb.log(Level.SEVERE, "Seb failed " + seb.getLabel(), e);
        	seb.report(FAILED_REPORT);
        }
	}

	@Override
	protected void finished(Description description) {
		if (seb != null)
			seb.quit();
	}

}
