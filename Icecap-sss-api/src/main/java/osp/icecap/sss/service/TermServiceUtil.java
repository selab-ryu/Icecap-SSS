/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package osp.icecap.sss.service;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for Term. This utility wraps
 * <code>osp.icecap.sss.service.impl.TermServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Jerry H. Seo, Won Cheol Ryu
 * @see TermService
 * @generated
 */
@ProviderType
public class TermServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>osp.icecap.sss.service.impl.TermServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static osp.icecap.sss.model.Term addTerm(
			String name, String version, String type, String strDisplayName,
			String strDefinition, String strTooltip, String[] synonyms,
			String attributes,
			com.liferay.portal.kernel.service.ServiceContext sc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTerm(
			name, version, type, strDisplayName, strDefinition, strTooltip,
			synonyms, attributes, sc);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static osp.icecap.sss.model.Term getTerm(long termId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTerm(termId);
	}

	public static TermService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<TermService, TermService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(TermService.class);

		ServiceTracker<TermService, TermService> serviceTracker =
			new ServiceTracker<TermService, TermService>(
				bundle.getBundleContext(), TermService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}