package osp.icecap.sss.web.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.persistence.PortletUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.trash.TrashHelper;

import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.security.permission.resource.TermModelPermissionHelper;
import osp.icecap.sss.web.util.TermActionDropdownItemsProvider;
import osp.icecap.sss.web.util.TermAdminActionDropdownItemsProvider;

public class TermVerticalCard extends BaseVerticalCard {
	public TermVerticalCard(
			Term term, 
			RenderRequest renderRequest,
			RenderResponse renderResponse,
			RowChecker rowChecker,
			TrashHelper trashHelper, 
			String termURL,
			PermissionChecker permissionChecker) {

			super(term, renderRequest, rowChecker);

			_term = term;
			_renderRequest = renderRequest;
			_renderResponse = renderResponse;
			_locale = renderRequest.getLocale();
			_trashHelper = trashHelper;
			_termURL = termURL;
			_permissionChecker = permissionChecker;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		System.out.println("TermVerticalCard.getActionDropdownItems() called.....");

			return null;
	}

	public String getAspectRatioCssClasses() {
		System.out.println("TermVerticalCard.getAspectRatioCssClasses() called.....");
		return "aspect-ratio-item-center-middle " +
			"aspect-ratio-item-vertical-fluid";
	}

	@Override
	public String getDefaultEventHandler() {
		System.out.println("TermVerticalCard.getDefaultEventHandler() called.....");
		return IcecapSSSConstants.TERM_ELEMENTS_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getHref() {
		System.out.println("TermVerticalCard.getHref() called.....");
		try {
			if (!TermModelPermissionHelper.contains(
					_permissionChecker, _term, ActionKeys.UPDATE)) {

				return null;
			}
		} catch (PortalException e) {
			e.printStackTrace();
		}

		return _termURL;
	}

	@Override
	public String getIcon() {
		System.out.println("TermVerticalCard.getIcon() called.....");
		return "term";
	}

	@Override
	public String getImageSrc() {
		System.out.println("TermVerticalCard.getImageSrc() called.....");
		return "imageSrc";
	}

	@Override
	public String getSubtitle() {
		System.out.println("TermVerticalCard.getSubtitle() called.....");
		return _term.getDefinition(_locale);
	}

	@Override
	public String getTitle() {
		System.out.println("TermVerticalCard.getTitle() called.....");
		return HtmlUtil.escape( _term.getDisplayTitle(_locale) );
	}

	private final Term _term;
	private final String _termURL;
	private final PermissionChecker _permissionChecker;
	private final RenderRequest  _renderRequest;
	private final RenderResponse _renderResponse;
	private final TrashHelper _trashHelper;
	private final Locale _locale;
}
