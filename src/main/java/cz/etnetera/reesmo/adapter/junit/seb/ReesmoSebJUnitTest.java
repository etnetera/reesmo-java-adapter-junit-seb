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

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import cz.etnetera.seb.Seb;

/**
 * Base class for JUnit Seb tests backed by Reesmo. It process Seb test and
 * reports result with attachments into Reesmo including failed report o in case
 * of failed test. For using it just implement {@link ReesmoSebJUnitTest#createSeb()}
 * method.
 */
abstract public class ReesmoSebJUnitTest<T extends Seb> {

	public static final String FAILED_REPORT = "FAILED";

	protected T seb;

	protected TestName testName = new TestName();

	protected ReesmoSebJUnitAdapter reesmoAdapter = new ReesmoSebJUnitAdapter();

	protected SebFailedReporter sebFailedReporter = new SebFailedReporter();

	@Rule
	public RuleChain chainedRules = RuleChain.outerRule(testName).around(reesmoAdapter).around(sebFailedReporter);

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		seb = (T) createSeb().withListener(reesmoAdapter.getSebListener())
				.withLabel(getClass().getSimpleName(), testName.getMethodName()).start();
		sebFailedReporter.setSeb(seb);
	}

	/**
	 * This methods should create {@link Seb} instance. It is called from
	 * {@link ReesmoSebJUnitTest#before()} and appends Reesmo listener, label and
	 * {@link SebFailedReporter}.
	 * 
	 * @return The new {@link Seb} instance.
	 */
	abstract protected T createSeb();

	public class SebFailedReporter extends TestWatcher {

		protected Seb seb;

		public void setSeb(Seb seb) {
			this.seb = seb;
		}

		@Override
		protected void failed(Throwable e, Description description) {
			if (seb != null)
				seb.report(FAILED_REPORT);
		}

		@Override
		protected void finished(Description description) {
			if (seb != null)
				seb.quit();
		}

	}

}
