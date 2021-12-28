package osp.icecap.sss.web.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseHorizontalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.trash.TrashHelper;

import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import osp.icecap.sss.model.Term;
import osp.icecap.sss.security.permission.resource.TermModelPermissionHelper;
import osp.icecap.sss.web.util.TermActionDropdownItemsProvider;
import osp.icecap.sss.web.util.TermAdminActionDropdownItemsProvider;

public class TermHorizontalCard extends BaseHorizontalCard {
	
	private final Term _term;
	private final String _termURL;
	private final PermissionChecker _permissionChecker;
	private final RenderRequest  _renderRequest;
	private final RenderResponse _renderResponse;
	private final TrashHelper _trashHelper;
	private final Locale _locale;

	public TermHorizontalCard(
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
	public String getIcon() {
		System.out.println("TermHorizontalCard.getIcon() called.....");
		return "term";
	}

	@Override
	public String getTitle() {
		System.out.println("TermHorizontalCard.getTitle() called.....");
		return HtmlUtil.escape( _term.getDisplayTitle(_locale) );
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		System.out.println("TermHorizontalCard.getActionDropdownItems() called.....");

		return null;
	}

	@Override
	public String getHref() {
		System.out.println("TermHorizontalCard.getHref() called.....");
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
}
