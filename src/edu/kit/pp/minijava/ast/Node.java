package edu.kit.pp.minijava.ast;

public class Node {

	public String print() {
		return "";
	}

	public String toString() {
		return toString(0);
	}

	private String toString(int deep) {
		String indention = "";
		if (deep > 0)
			indention = String.format(String.format("%%0%dd", deep), 0).replace("0", "-");

		String result = indention + getClass().getSimpleName() + "\n";

		Class klass = this.getClass();
		do {
			for (java.lang.reflect.Field field : klass.getDeclaredFields()) {
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
						System.out.println(ex.getLocalizedMessage());
					}
				}
				else {
					try {
						Object o = field.get(this);
						if (o != null) {
							if (o instanceof Node)
								result += ((Node) o).toString(deep + 1);
							else {
								String name = field.getName().substring(1);
								result += indention + "-" + name + ": " + o.toString() + "\n";
							}
						}
					} catch (IllegalAccessException ex) {
						System.out.println(ex.getLocalizedMessage());
					}
				}
			}
			klass = klass.getSuperclass();
		} while (klass != Object.class);
		return result;
	}

}