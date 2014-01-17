package com.liferay.portal.audit.hook.listeners;

import java.util.List;

import com.liferay.portal.audit.hook.listeners.util.Attribute;
import com.liferay.portal.audit.hook.listeners.util.AttributesBuilder;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

public class JournalArticleListener extends AbstractListener<JournalArticle> {

	@Override
	protected List<Attribute> getModifiedAttributes(
		final JournalArticle newJournalArticle, 
		final JournalArticle oldJournalArticle) {

		final AttributesBuilder attributesBuilder = 
			new AttributesBuilder(
				newJournalArticle, oldJournalArticle);

		attributesBuilder.add("articleId");
		attributesBuilder.add("version");
		attributesBuilder.add("title");
		attributesBuilder.add("urlTitle");
		attributesBuilder.add("description");
		attributesBuilder.add("content");
		attributesBuilder.add("type");
		attributesBuilder.add("structureId");
		attributesBuilder.add("templateId");
		attributesBuilder.add("layoutUuid");
		attributesBuilder.add("displayDate");
		attributesBuilder.add("expirationDate");
		attributesBuilder.add("reviewDate");
		attributesBuilder.add("indexable");
		attributesBuilder.add("smallImage");
		attributesBuilder.add("smallImageId");
		attributesBuilder.add("smallImageURL");
		attributesBuilder.add("status");
		attributesBuilder.add("statusByUserId");
		attributesBuilder.add("statusByUserName");
		attributesBuilder.add("statusDate");

		return attributesBuilder.getAttributes();

	}

	@Override
	protected void configureMessage(final AuditMessage auditMessage,
		final JournalArticle newModel) {

		auditMessage.setUserId(newModel.getUserId());
		auditMessage.setUserName(newModel.getUserName());
		auditMessage.setClassPK(newModel.getArticleId());
		auditMessage.setCompanyId(newModel.getCompanyId());

	}

	@Override
	protected JournalArticle getOldModel(long id) throws SystemException {
		return JournalArticleLocalServiceUtil.fetchJournalArticle(id);
	}

}
