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

import cz.etnetera.reesmo.adapter.junit.ReesmoJUnitBridge;
import cz.etnetera.reesmo.adapter.junit.ReesmoJUnitTest;
import cz.etnetera.reesmo.writer.model.result.TestCategory;
import cz.etnetera.reesmo.writer.model.result.TestType;
import cz.etnetera.reesmo.writer.storage.FileWithPath;
import cz.etnetera.seb.Seb;
import cz.etnetera.seb.event.impl.OnFileSaveEvent;
import cz.etnetera.seb.listener.SebListener;

/**
 * Simple interface which propagates {@link Seb} attachments into Reesmo
 * results.
 */
public interface ReesmoSebJUnitTest extends ReesmoJUnitTest {

	/**
	 * This will be called from
	 * {@link ReesmoJUnitTest#registerReesmoBridge(ReesmoJUnitBridge)}, so
	 * {@link Seb} should be constructed in methods annotated with
	 * {@link Before}.
	 * 
	 * @return
	 */
	Seb getSeb();

	@Override
	default void registerReesmoBridge(ReesmoJUnitBridge bridge) {
		bridge.getResult().addType(TestType.SELENIUM).addType(TestType.SEB).addCategory(TestCategory.FUNCTIONAL);
		Seb seb = getSeb();
		if (seb != null) {
			registerSebForReesmo(bridge, seb);
		}
	}

	/**
	 * Allows to register additional {@link Seb} instance into Reesmo.
	 * {@link ReesmoJUnitBridge} persistence needs to be implemented in
	 * {@link ReesmoSebJUnitTest#registerReesmoBridge(ReesmoJUnitBridge)} method
	 * so it can be passed in this method after.
	 * 
	 * @param bridge
	 * @param seb
	 */
	default void registerSebForReesmo(ReesmoJUnitBridge bridge, Seb seb) {
		seb.addListener(new SebListener() {

			@Override
			public void onFileSave(OnFileSaveEvent event) {
				bridge.addAttachment(new FileWithPath(event.getFile(), "seb/"
						+ event.getSeb().getReportDir().toPath().relativize(event.getFile().toPath()).toString()));
			}

		});
	}

}
