package cs3500.pa05.model;

/**
 * class PlannedItem
 */
public abstract class PlannedItem {

  /**
   * the name for this PlannedItem
   */
  protected final String name;

  /**
   * the description for this PlannedItem
   */
  protected StringBuilder description;

  /**
   * the category for this PlannedItem, can be empty
   */
  protected String category;

  /**
   * constructor for PlannedItem
   *
   * @param name item name
   */
  protected PlannedItem(String name) {
    this.name = name;
    this.description = new StringBuilder();
    this.category = "";
  }

  /**
   * Returns the name of the planned item.
   *
   * @return The name of the planned item.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Appends to the description of the planned item.
   *
   * @param s The string to be appended to the description.
   */
  public void setDescription(String s) {
    description.append(s);
  }

  /**
   * Returns the description of the planned item.
   *
   * @return The description of the planned item.
   */
  public String getDescription() {
    return description.toString();
  }

  /**
   * Sets the category of the planned item.
   *
   * @param category The category to be set.
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * Returns the category of the planned item.
   *
   * @return The category of the planned item.
   */
  public String getCategory() {
    return category;
  }

  /**
   * Checks if the planned item is in a specific category.
   *
   * @param c The category to check.
   * @return true if the planned item is in the category, false otherwise.
   */
  public Boolean inCategory(String c) {
    return this.category.equals(c);
  }

  /**
   * Compares this planned item with another planned item based on name.
   *
   * @param item The planned item to compare with.
   * @return The comparison result as an integer.
   */
  public int compareName(PlannedItem item) {
    return item.name.compareTo(this.name);
  }

}
