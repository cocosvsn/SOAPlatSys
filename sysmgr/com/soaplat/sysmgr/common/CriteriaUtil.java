package com.soaplat.sysmgr.common;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import flex.messaging.io.amf.ASObject;

public class CriteriaUtil {
	public static Criterion addToRes(ASObject asobj) {
		Set<Object> set = asobj.keySet();
		Iterator<Object> it = set.iterator();
		String op = "eq";
		String property = "";
		String value = "";
		if (asobj.containsKey("_op"))
			op = (String) asobj.get("_op");
		while (it.hasNext()) {
			property = it.next().toString();
			if (!property.startsWith("_")) {
				value = (String) asobj.get(property);
				break;
			}
		}

		if ("like".equalsIgnoreCase(op))
			return Restrictions.like(property, value);
		else if ("longeq".equalsIgnoreCase(op))
			return Restrictions.eq(property, Long.parseLong(value));
		else if ("inteq".equalsIgnoreCase(op))
			return Restrictions.eq(property, Integer.parseInt(value));
		else if ("eq".equalsIgnoreCase(op))
			return Restrictions.eq(property, value);
		else if ("gt".equalsIgnoreCase(op))
			return Restrictions.gt(property, value);
		else if ("lt".equalsIgnoreCase(op))
			return Restrictions.lt(property, value);
		else if ("timegt".equalsIgnoreCase(op))
			return Restrictions.gt(property, new Date(Long.parseLong(value)));
		else if ("timelt".equalsIgnoreCase(op))
			return Restrictions.lt(property, new Date(Long.parseLong(value)));
		else if ("timege".equalsIgnoreCase(op))
			return Restrictions.ge(property, new Date(Long.parseLong(value)));
		else if ("timele".equalsIgnoreCase(op))
			return Restrictions.le(property, new Date(Long.parseLong(value)));
		else if ("dougt".equalsIgnoreCase(op))
			return Restrictions.gt(property, Double.parseDouble((value)));
		else if ("doult".equalsIgnoreCase(op))
			return Restrictions.lt(property, Double.parseDouble((value)));
		else if ("douge".equalsIgnoreCase(op))
			return Restrictions.ge(property, Double.parseDouble((value)));
		else if ("doule".equalsIgnoreCase(op))
			return Restrictions.le(property, Double.parseDouble((value)));
		else if ("ge".equalsIgnoreCase(op))
			return Restrictions.ge(property, value);
		else if ("le".equalsIgnoreCase(op))
			return Restrictions.le(property, value);
		else if ("isNull".equalsIgnoreCase(op))
			return Restrictions.isNull(property);
		else if ("isNotNull".equalsIgnoreCase(op))
			return Restrictions.isNotNull(property);
		else if ("between".equalsIgnoreCase(op))
			return Restrictions.between(property, (String) asobj.get("_min"),
					(String) asobj.get("_max"));
		else if ("longge".equalsIgnoreCase(op))
			return Restrictions.ge(property, Long.parseLong(value));

		return null;
	}

	public static Projection addToPro(ASObject asobj) {
		if (asobj.containsKey("_field"))
			return Projections.property((String) asobj.get("_field"));
		if (asobj.containsKey("_avg"))
			return Projections.avg((String) asobj.get("_avg"));
		if (asobj.containsKey("_countDistinct"))
			return Projections.countDistinct((String) asobj
					.get("_countDistinct"));
		if (asobj.containsKey("_count"))
			return Projections.count((String) asobj.get("_count"));
		if (asobj.containsKey("_max"))
			return Projections.max((String) asobj.get("_max"));
		if (asobj.containsKey("_min"))
			return Projections.min((String) asobj.get("_min"));
		if (asobj.containsKey("_sum"))
			return Projections.sum((String) asobj.get("_sum"));
		if (asobj.containsKey("_rowCount"))
			return Projections.rowCount();
		return null;
	}
}
