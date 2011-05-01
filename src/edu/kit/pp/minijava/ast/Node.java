package edu.kit.pp.minijava.ast;

public class Node {

	public String toString() {
		return toString(0);
	}

	private String toString(int deep) {
		String indention = "";
		if (deep > 0)
			indention = String.format(String.format("%%0%dd", deep), 0).replace("0", "-");

		String result = indention + getClass().getSimpleName();
		if (this instanceof Expression) {
			result += " (" + ((Expression)this)._token + ")";
		}
		result += "\n";

		java.lang.reflect.Field[] fields = getClass().getDeclaredFields();

		for (java.lang.reflect.Field field : fields) {
			field.setAccessible(true);
			if (field.getType() == java.util.List.class) {
				try {
					java.util.List l = (java.util.List) field.get(this);
					for (Object o : l) {
						if (o instanceof Node)
							result += ((Node)o).toString(deep + 1);
						else
							result += indention + o.toString();
					}
				}
				catch (IllegalAccessException ex) {
					System.out.println("illegal");
				}
			}
			else {
				try {
					Object o = field.get(this);
					if (o != null) {
						if (o instanceof Node)
							result += ((Node) o).toString(deep + 1);
						else
							result += indention + "-" +  o.toString() + "\n";
					}
				} catch (IllegalAccessException ex) {
					System.out.println(ex.getLocalizedMessage());
				}
			}
		}
		return result;
	}

}