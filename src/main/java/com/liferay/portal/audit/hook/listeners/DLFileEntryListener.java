package com.liferay.portal.audit.hook.listeners;

import java.util.List;

import com.liferay.portal.audit.hook.listeners.util.Attribute;
import com.liferay.portal.audit.hook.listeners.util.AttributesBuilder;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

public class DLFileEntryListener extends AbstractListener<DLFileEntry> {

	@Override
	protected List<Attribute> getModifiedAttributes(final DLFileEntry newModel,
		final DLFileEntry oldModel) {

		final AttributesBuilder attributesBuilder = 
				new AttributesBuilder(newModel, oldModel);

		attributesBuilder.add("repositoryId");
		attributesBuilder.add("folderId");
		attributesBuilder.add("name");
		attributesBuilder.add("extension");
		attributesBuilder.add("mimeType");
		attributesBuilder.add("title");
		attributesBuilder.add("description");
		attributesBuilder.add("extraSettings");
		attributesBuilder.add("fileEntryTypeId");
		attributesBuilder.add("version");
		attributesBuilder.add("size");
		attributesBuilder.add("readCount");
		attributesBuilder.add("smallImageId");
		attributesBuilder.add("largeImageId");
		attributesBuilder.add("custom1ImageId");
		attributesBuilder.add("custom2ImageId");

		return attributesBuilder.getAttributes();

	}

	@Override
	protected DLFileEntry getOldModel(long id) throws SystemException {
		return DLFileEntryLocalServiceUtil.fetchDLFileEntry(id);
	}

	@Override
	protected long getPrimaryKeyObj(final DLFileEntry model) {
		return model.getFileEntryId();
	}

}
