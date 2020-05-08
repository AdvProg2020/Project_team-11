package view.menu.productsMenu;

import controller.AllAccountZone;
import view.menu.Menu;
import view.menu.auctionMenu.AuctionMenu;

import java.util.ArrayList;

public class FilteringMenu extends Menu {

    public FilteringMenu(Menu parentMenu) {
        super("Filtering", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getShowAvailableFilters());
        submenus.add(getFilterMenu());
        submenus.add(getCurrentFiltersMenu());
        submenus.add(getDisableFilterMenu());
        this.setSubmenus(submenus);
    }

    private Menu getShowAvailableFilters() {
        return new Menu("Show Available Filters", this) {
            @Override
            public void execute() {
                if (parentMenu.getParentMenu() instanceof ProductsMenu) {
                    if (ProductsMenu.getFilter().getCategory().equals("")) {
                        System.out.println(AllAccountZone.getCategoriesForFilter() + "min price | max price");
                    } else {
                        String features = "";
                        for (String feature : ProductsMenu.getFilter().getFeature().keySet()) {
                            features += feature + " | ";
                        }
                        features += "min price | max price";
                        System.out.println(features);
                    }
                } else {
                    if (AuctionMenu.getFilter().getCategory().equals("")) {
                        System.out.println(AllAccountZone.getCategoriesForFilter() + "min price | max price");
                    } else {
                        String features = "";
                        for (String feature : AuctionMenu.getFilter().getFeature().keySet()) {
                            features += feature + " | ";
                        }
                        features += "min price | max price";
                        System.out.println(features);
                    }
                }
                this.parentMenu.execute();
            }
        };
    }

    private Menu getFilterMenu() {
        return new Menu("Filter", this) {
            @Override
            public void execute() {
                if (parentMenu.getParentMenu() instanceof ProductsMenu) {
                    if (ProductsMenu.getFilter().getCategory().equals("")) {
                        String field = checkInput("Enter an available filter",
                                AllAccountZone.getCategoriesRegex() + "min price|max price)");
                        setFilterInfoForCategory(field);
                    } else {
                        String featureRegex = "(?i)(";
                        for (String feature : ProductsMenu.getFilter().getFeature().keySet()) {
                            featureRegex += feature + "|";
                        }
                        featureRegex += "min price|max price)";
                        String field = checkInput("Enter an available filter", featureRegex);
                        setFilterInfoForCategoryFeature(field);
                    }
                } else {
                    if (AuctionMenu.getFilter().getCategory().equals("")) {
                        String field = checkInput("Enter an available filter",
                                AllAccountZone.getCategoriesRegex() + "min price|max price)");
                        setFilterInfoForCategory(field);
                    } else {
                        String featureRegex = "(?i)(";
                        for (String feature : AuctionMenu.getFilter().getFeature().keySet()) {
                            featureRegex += feature + "|";
                        }
                        featureRegex += "min price|max price)";
                        String field = checkInput("Enter an available filter", featureRegex);
                        setFilterInfoForCategoryFeature(field);
                    }
                }
                this.parentMenu.execute();
            }
        };
    }

    private Menu getCurrentFiltersMenu() {
        return new Menu("Show Current Filters", this) {
            @Override
            public void execute() {
                FilterInfo filter;
                if (parentMenu.getParentMenu() instanceof ProductsMenu)
                    filter = ProductsMenu.getFilter();
                else
                    filter = AuctionMenu.getFilter();
                System.out.println(filter.toString());
                this.parentMenu.execute();
            }
        };
    }

    private Menu getDisableFilterMenu() {
        return new Menu("Disable Filter", this) {
            @Override
            public void execute() {
                if (parentMenu.getParentMenu() instanceof ProductsMenu) {
                    if (ProductsMenu.getFilter().getCategory().equals("")) {
                        String field = checkInput("Enter a selected filter", "(?i)(min price|max price)");
                        if (field.equalsIgnoreCase("min price")) {
                            ProductsMenu.getFilter().setMinimumPrice(0);
                        } else if (field.equalsIgnoreCase("max price")) {
                            ProductsMenu.getFilter().setMaximumPrice(Long.MAX_VALUE);
                        }
                    } else {
                        String featureRegex = "(?i)(";
                        for (String feature : ProductsMenu.getFilter().getFeature().keySet()) {
                            featureRegex += feature + "|";
                        }
                        featureRegex += "min price|max price)";
                        String field = checkInput("Enter a selected filter", featureRegex);
                        if (field.equalsIgnoreCase("min price")) {
                            ProductsMenu.getFilter().setMinimumPrice(0);
                        } else if (field.equalsIgnoreCase("max price")) {
                            ProductsMenu.getFilter().setMaximumPrice(Long.MAX_VALUE);
                        } else {
                            ProductsMenu.getFilter().getFeature().replace(field, "");
                        }
                    }
                } else {
                    if (AuctionMenu.getFilter().getCategory().equals("")) {
                        String field = checkInput("Enter a selected filter", "(?i)(min price|max price)");
                        if (field.equalsIgnoreCase("min price")) {
                            AuctionMenu.getFilter().setMinimumPrice(0);
                        } else if (field.equalsIgnoreCase("max price")) {
                            AuctionMenu.getFilter().setMaximumPrice(Long.MAX_VALUE);
                        }
                    } else {
                        String featureRegex = "(?i)(";
                        for (String feature : AuctionMenu.getFilter().getFeature().keySet()) {
                            featureRegex += feature + "|";
                        }
                        featureRegex += "min price|max price)";
                        String field = checkInput("Enter a selected filter", featureRegex);
                        if (field.equalsIgnoreCase("min price")) {
                            AuctionMenu.getFilter().setMinimumPrice(0);
                        } else if (field.equalsIgnoreCase("max price")) {
                            AuctionMenu.getFilter().setMaximumPrice(Long.MAX_VALUE);
                        } else {
                            AuctionMenu.getFilter().getFeature().replace(field, "");
                        }
                    }
                }
                this.parentMenu.execute();
            }
        };
    }

    private void setFilterInfoForCategory(String field) {
        if (parentMenu.getParentMenu() instanceof ProductsMenu) {
            if (field.equalsIgnoreCase("min price")) {
                long minPrice = Long.parseLong(checkInput("Enter minimum Price", "\\d+"));
                ProductsMenu.getFilter().setMinimumPrice(minPrice);
            } else if (field.equalsIgnoreCase("max price")) {
                long maxPrice = Long.parseLong(checkInput("Enter maximum Price", "\\d+"));
                ProductsMenu.getFilter().setMaximumPrice(maxPrice);
            } else {
                ProductsMenu.getFilter().setCategory(field);
                AllAccountZone.setFilterCategoryFeature(field, parentMenu.getParentMenu());
            }
        } else {
            if (field.equalsIgnoreCase("min price")) {
                long minPrice = Long.parseLong(checkInput("Enter minimum Price", "\\d+"));
                AuctionMenu.getFilter().setMinimumPrice(minPrice);
            } else if (field.equalsIgnoreCase("max price")) {
                long maxPrice = Long.parseLong(checkInput("Enter maximum Price", "\\d+"));
                AuctionMenu.getFilter().setMaximumPrice(maxPrice);
            } else {
                AuctionMenu.getFilter().setCategory(field);
                AllAccountZone.setFilterCategoryFeature(field, parentMenu.getParentMenu());
            }
        }
    }

    private void setFilterInfoForCategoryFeature(String field) {
        if (parentMenu.getParentMenu() instanceof ProductsMenu) {
            if (field.equalsIgnoreCase("min price")) {
                long minPrice = Long.parseLong(checkInput("Enter minimum Price", "\\d+"));
                ProductsMenu.getFilter().setMinimumPrice(minPrice);
            } else if (field.equalsIgnoreCase("max price")) {
                long maxPrice = Long.parseLong(checkInput("Enter maximum Price", "\\d+"));
                ProductsMenu.getFilter().setMaximumPrice(maxPrice);
            } else {
                String filter = checkInput("Enter " + field, ".+");
                ProductsMenu.getFilter().getFeature().replace(field, filter);
            }
        } else {
            if (field.equalsIgnoreCase("min price")) {
                long minPrice = Long.parseLong(checkInput("Enter minimum Price", "\\d+"));
                AuctionMenu.getFilter().setMinimumPrice(minPrice);
            } else if (field.equalsIgnoreCase("max price")) {
                long maxPrice = Long.parseLong(checkInput("Enter maximum Price", "\\d+"));
                AuctionMenu.getFilter().setMaximumPrice(maxPrice);
            } else {
                String filter = checkInput("Enter " + field, ".+");
                AuctionMenu.getFilter().getFeature().replace(field, filter);
            }
        }
    }
}
