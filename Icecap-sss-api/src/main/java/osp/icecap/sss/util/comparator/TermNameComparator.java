package osp.icecap.sss.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;

import osp.icecap.sss.model.Term;

public class TermNameComparator extends OrderByComparator<Term> {

	public static final String ORDER_BY_ASC = "Term.name ASC";

	public static final String ORDER_BY_DESC = "Term.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public TermNameComparator() {
		this(false);
	}

	public TermNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Term entry1, Term entry2) {
		String title1 = entry1.getName();
		String title2 = entry2.getName();

		int value = title1.compareTo(title2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}
