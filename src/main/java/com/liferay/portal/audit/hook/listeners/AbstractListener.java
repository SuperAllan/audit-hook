package com.liferay.portal.audit.hook.listeners;

import java.util.List;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.audit.hook.listeners.util.Attribute;
import com.liferay.portal.audit.hook.listeners.util.AuditMessageBuilder;
import com.liferay.portal.audit.util.EventTypes;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.BaseModelListener;

public abstract class AbstractListener<T extends BaseModel<T>> 
	extends BaseModelListener<T> {

	@Override
	public void onBeforeCreate(final T newModel)
			throws ModelListenerException {

		auditMessage(EventTypes.ADD, newModel);
		super.onBeforeCreate(newModel);

	}

	@Override
	public void onBeforeRemove(final T newModel)
			throws ModelListenerException {

		auditMessage(EventTypes.DELETE, newModel);
		super.onBeforeRemove(newModel);

	}

	@Override
	public void onBeforeUpdate(final T newModel)
			throws ModelListenerException {

		auditMessage(EventTypes.UPDATE, newModel);
		super.onBeforeUpdate(newModel);

	}

	@Override
	public void onBeforeAddAssociation(final Object classPK,
			final String associationClassName, final Object associationClassPK)
			throws ModelListenerException {
		super.onBeforeAddAssociation(classPK, associationClassName,
				associationClassPK);
	}

	@Override
	public void onBeforeRemoveAssociation(final Object classPK,
			final String associationClassName, final Object associationClassPK)
			throws ModelListenerException {
		super.onBeforeRemoveAssociation(classPK, associationClassName,
				associationClassPK);
	}

	protected final void auditMessage(
		final String eventType, final T newModel)
			throws ModelListenerException {

		try {

			final long classPK = (Long) newModel.getPrimaryKeyObj();
			final T oldModel = getOldModel(classPK);
			
			final List<Attribute> attributes = getModifiedAttributes(
					newModel, oldModel);

			final AuditMessage auditMessage =
					AuditMessageBuilder.buildAuditMessage(
							eventType, _getQualifiedNameInterface(newModel),
							classPK, attributes);

			configureMessage(auditMessage, newModel);

			AuditRouterUtil.route(auditMessage);

		}
		catch (final Exception e) {
			throw new ModelListenerException(e);
		}

	}

	protected abstract void configureMessage(final AuditMessage auditMessage,
		final T newModel);

	protected abstract T getOldModel(final long id) throws SystemException;
	
	protected abstract List<Attribute> getModifiedAttributes(
			final T newModel, 
			final T oldModel);

	private String _getQualifiedNameInterface(final T model) {
		return model.getClass().getSuperclass().getInterfaces()[0].getName();
	}
}
