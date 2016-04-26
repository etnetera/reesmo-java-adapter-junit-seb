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

import cz.etnetera.reesmo.adapter.junit.ReesmoJUnitAdapter;
import cz.etnetera.reesmo.writer.storage.ExtendedFile;
import cz.etnetera.seb.event.impl.OnFileSaveEvent;
import cz.etnetera.seb.listener.SebListener;

public class ReesmoSebJUnitAdapter extends ReesmoJUnitAdapter {

	public SebListener getSebListener() {
		return new SebListener() {

			@Override
			public void onFileSave(OnFileSaveEvent event) {
				addAttachment(ExtendedFile.withPath(event.getFile(), "seb/"
						+ event.getSeb().getReportDir().toPath().relativize(event.getFile().toPath()).toString()));
			}

		};
	}

}
