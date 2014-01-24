package com.liferay.portal.audit.hook.listeners;

import com.liferay.portal.audit.hook.listeners.util.Attribute;
import com.liferay.portal.audit.hook.listeners.util.AttributesBuilder;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;

import java.util.List;

public class MBThreadListener extends AbstractListener<MBThread> {

	@Override
	protected void copyPropertiesToAuditMessage(
			final AuditMessage auditMessage, final MBThread model) {
		super.copyPropertiesToAuditMessage(auditMessage, model);
		auditMessage.setUserId(model.getStatusByUserId());
		auditMessage.setUserName(model.getStatusByUserName());
	}

	@Override
	protected List<Attribute> getModifiedAttributes(final MBThread newModel,
			final MBThread oldModel) {

		final AttributesBuilder attributesBuilder = 
			new AttributesBuilder(newModel, oldModel);

		attributesBuilder.add("categoryId");
		attributesBuilder.add("rootMessageId");
		attributesBuilder.add("rootMessageUserId");
		attributesBuilder.add("messageCount");
		attributesBuilder.add("viewCount");
		attributesBuilder.add("lastPostByUserId");
		attributesBuilder.add("lastPostDate");
		attributesBuilder.add("priority");
		attributesBuilder.add("question");
		attributesBuilder.add("status");
		attributesBuilder.add("statusByUserId");
		attributesBuilder.add("statusByUserName");
		attributesBuilder.add("statusDate");

		return attributesBuilder.getAttributes();

	}

	@Override
	protected MBThread getOldModel(final long id) throws SystemException {
		return MBThreadLocalServiceUtil.fetchThread(id);
	}

	@Override
	protected long getPrimaryKeyObj(final MBThread model) {
		return model.getThreadId();
	}

}
