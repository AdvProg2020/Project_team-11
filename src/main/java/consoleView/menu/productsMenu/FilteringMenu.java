package consoleView.menu.productsMenu;

import controller.AllAccountZone;
import consoleView.menu.Menu;
import consoleView.menu.auctionMenu.AuctionMenu;

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
                        System.out.println(AllAccountZone.getCategoriesForFilter() + "min price | max price |" +
                                " product name | seller name | company | min stock status");
                    } else {
                        String features = "";
                        for (String feature : ProductsMenu.getFilter().getFeature().keySet()) {
                            features += feature + " | ";
                        }
                        features += "min price | max price | product name | seller name | company | min stock status";
                        System.out.println(features);
                    }
                } else {
                    if (AuctionMenu.getFilter().getCategory().equals("")) {
                        System.out.println(AllAccountZone.getCategoriesForFilter() + "min price | max price | " +
                                "product name | seller name | company | min stock status");
                    } else {
                        String features = "";
                        for (String feature : AuctionMenu.getFilter().getFeature().keySet()) {
                            features += feature + " | ";
                        }
                        features += "min price | max price | product name | seller name | company | min stock status";
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
                                AllAccountZone.getCategoriesRegex() + "min price|max price|product name|" +
                                        "seller name|company|min stock status)");
                        setFilterInfoForCategory(field);
                    } else {
                        String featureRegex = "(?i)(";
                        for (String feature : ProductsMenu.getFilter().getFeature().keySet()) {
                            featureRegex += feature + "|";
                        }
                        featureRegex += "min price|max price|product name|seller name|company|min stock status)";
                        String field = checkInput("Enter an available filter", featureRegex);
                        setFilterInfoForCategoryFeature(field);
                    }
                } else {
                    if (AuctionMenu.getFilter().getCategory().equals("")) {
                        String field = checkInput("Enter an available filter",
                                AllAccountZone.getCategoriesRegex() + "min price|max price|product name|" +
                                        "seller name|company|min stock status)");
                        setFilterInfoForCategory(field);
                    } else {
                        String featureRegex = "(?i)(";
                        for (String feature : AuctionMenu.getFilter().getFeature().keySet()) {
                            featureRegex += feature + "|";
                        }
                        featureRegex += "min price|max price|product name|seller name|company|min stock status)";
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
                        String field = checkInput("Enter a selected filter", "(?i)(min price|max price|" +
                                "product name|seller name|company|min stock status)");
                        if (field.equalsIgnoreCase("min price")) {
                            ProductsMenu.getFilter().setMinimumPrice(0);
                        } else if (field.equalsIgnoreCase("max price")) {
                            ProductsMenu.getFilter().setMaximumPrice(Long.MAX_VALUE);
                        } else if (field.equalsIgnoreCase("product name")) {
                            ProductsMenu.getFilter().setProductName("");
                        } else if (field.equalsIgnoreCase("seller name")) {
                            ProductsMenu.getFilter().setSellerName("");
                        } else if (field.equalsIgnoreCase("company")) {
                            ProductsMenu.getFilter().setCompany("");
                        } else if (field.equalsIgnoreCase("min stock status")) {
                            ProductsMenu.getFilter().setMinimumStockStatus(0);
                        }
                    } else {
                        String featureRegex = "(?i)(";
                        for (String feature : ProductsMenu.getFilter().getFeature().keySet()) {
                            featureRegex += feature + "|";
                        }
                        featureRegex += "min price|max price|product name|seller name|company|min stock status)";
                        String field = checkInput("Enter a selected filter", featureRegex);
                        if (field.equalsIgnoreCase("min price")) {
                            ProductsMenu.getFilter().setMinimumPrice(0);
                        } else if (field.equalsIgnoreCase("max price")) {
                            ProductsMenu.getFilter().setMaximumPrice(Long.MAX_VALUE);
                        } else if (field.equalsIgnoreCase("product name")) {
                            ProductsMenu.getFilter().setProductName("");
                        } else if (field.equalsIgnoreCase("seller name")) {
                            ProductsMenu.getFilter().setSellerName("");
                        } else if (field.equalsIgnoreCase("company")) {
                            ProductsMenu.getFilter().setCompany("");
                        } else if (field.equalsIgnoreCase("min stock status")) {
                            ProductsMenu.getFilter().setMinimumStockStatus(0);
                        } else {
                            ProductsMenu.getFilter().getFeature().replace(field, "");
                        }
                    }
                } else {
                    if (AuctionMenu.getFilter().getCategory().equals("")) {
                        String field = checkInput("Enter a selected filter", "(?i)(min price|max price|" +
                                "product name|seller name|company|min stock status)");
                        if (field.equalsIgnoreCase("min price")) {
                            AuctionMenu.getFilter().setMinimumPrice(0);
                        } else if (field.equalsIgnoreCase("max price")) {
                            AuctionMenu.getFilter().setMaximumPrice(Long.MAX_VALUE);
                        } else if (field.equalsIgnoreCase("product name")) {
                            ProductsMenu.getFilter().setProductName("");
                        } else if (field.equalsIgnoreCase("seller name")) {
                            ProductsMenu.getFilter().setSellerName("");
                        } else if (field.equalsIgnoreCase("company")) {
                            ProductsMenu.getFilter().setCompany("");
                        } else if (field.equalsIgnoreCase("min stock status")) {
                            ProductsMenu.getFilter().setMinimumStockStatus(0);
                        }
                    } else {
                        String featureRegex = "(?i)(";
                        for (String feature : AuctionMenu.getFilter().getFeature().keySet()) {
                            featureRegex += feature + "|";
                        }
                        featureRegex += "min price|max price|product name|seller name|company|min stock status)";
                        String field = checkInput("Enter a selected filter", featureRegex);
                        if (field.equalsIgnoreCase("min price")) {
                            AuctionMenu.getFilter().setMinimumPrice(0);
                        } else if (field.equalsIgnoreCase("max price")) {
                            AuctionMenu.getFilter().setMaximumPrice(Long.MAX_VALUE);
                        } else if (field.equalsIgnoreCase("product name")) {
                            ProductsMenu.getFilter().setProductName("");
                        } else if (field.equalsIgnoreCase("seller name")) {
                            ProductsMenu.getFilter().setSellerName("");
                        } else if (field.equalsIgnoreCase("company")) {
                            ProductsMenu.getFilter().setCompany("");
                        } else if (field.equalsIgnoreCase("min stock status")) {
                            ProductsMenu.getFilter().setMinimumStockStatus(0);
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
        if (parentMenu instanceof ProductsMenu) {
            if (field.equalsIgnoreCase("min price")) {
                long minPrice = Long.parseLong(checkInput("Enter minimum Price", "\\d+"));
                ProductsMenu.getFilter().setMinimumPrice(minPrice);
            } else if (field.equalsIgnoreCase("max price")) {
                long maxPrice = Long.parseLong(checkInput("Enter maximum Price", "\\d+"));
                ProductsMenu.getFilter().setMaximumPrice(maxPrice);
            } else if (field.equalsIgnoreCase("product name")) {
                String productName = checkInput("Enter product name", ".+");
                ProductsMenu.getFilter().setProductName(productName);
            } else if (field.equalsIgnoreCase("seller name")) {
                String sellerName = checkInput("Enter seller name", ".+");
                ProductsMenu.getFilter().setSellerName(sellerName);
            } else if (field.equalsIgnoreCase("company")) {
                String company = checkInput("Enter company", ".+");
                ProductsMenu.getFilter().setCompany(company);
            } else if (field.equalsIgnoreCase("min stock status")) {
                int minStockStatus = Integer.parseInt(checkInput("Enter min stock status", "\\d+"));
                ProductsMenu.getFilter().setMinimumStockStatus(minStockStatus);
            } else {
                ProductsMenu.getFilter().setCategory(field);
                AllAccountZone.setFilterCategoryFeature(field, parentMenu);
            }
        } else {
            if (field.equalsIgnoreCase("min price")) {
                long minPrice = Long.parseLong(checkInput("Enter minimum Price", "\\d+"));
                AuctionMenu.getFilter().setMinimumPrice(minPrice);
            } else if (field.equalsIgnoreCase("max price")) {
                long maxPrice = Long.parseLong(checkInput("Enter maximum Price", "\\d+"));
                AuctionMenu.getFilter().setMaximumPrice(maxPrice);
            } else if (field.equalsIgnoreCase("product name")) {
                String productName = checkInput("Enter product name", ".+");
                ProductsMenu.getFilter().setProductName(productName);
            } else if (field.equalsIgnoreCase("seller name")) {
                String sellerName = checkInput("Enter seller name", ".+");
                ProductsMenu.getFilter().setSellerName(sellerName);
            } else if (field.equalsIgnoreCase("company")) {
                String company = checkInput("Enter company", ".+");
                ProductsMenu.getFilter().setCompany(company);
            } else if (field.equalsIgnoreCase("min stock status")) {
                int minStockStatus = Integer.parseInt(checkInput("Enter min stock status", "\\d+"));
                ProductsMenu.getFilter().setMinimumStockStatus(minStockStatus);
            } else {
                AuctionMenu.getFilter().setCategory(field);
                AllAccountZone.setFilterCategoryFeature(field, parentMenu);
            }
        }
    }

    private void setFilterInfoForCategoryFeature(String field) {
        if (parentMenu instanceof ProductsMenu) {
            if (field.equalsIgnoreCase("min price")) {
                long minPrice = Long.parseLong(checkInput("Enter minimum Price", "\\d+"));
                ProductsMenu.getFilter().setMinimumPrice(minPrice);
            } else if (field.equalsIgnoreCase("max price")) {
                long maxPrice = Long.parseLong(checkInput("Enter maximum Price", "\\d+"));
                ProductsMenu.getFilter().setMaximumPrice(maxPrice);
            } else if (field.equalsIgnoreCase("product name")) {
                String productName = checkInput("Enter product name", ".+");
                ProductsMenu.getFilter().setProductName(productName);
            } else if (field.equalsIgnoreCase("seller name")) {
                String sellerName = checkInput("Enter seller name", ".+");
                ProductsMenu.getFilter().setSellerName(sellerName);
            } else if (field.equalsIgnoreCase("company")) {
                String company = checkInput("Enter company", ".+");
                ProductsMenu.getFilter().setCompany(company);
            } else if (field.equalsIgnoreCase("min stock status")) {
                int minStockStatus = Integer.parseInt(checkInput("Enter min stock status", "\\d+"));
                ProductsMenu.getFilter().setMinimumStockStatus(minStockStatus);
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
            } else if (field.equalsIgnoreCase("product name")) {
                String productName = checkInput("Enter product name", ".+");
                ProductsMenu.getFilter().setProductName(productName);
            } else if (field.equalsIgnoreCase("seller name")) {
                String sellerName = checkInput("Enter seller name", ".+");
                ProductsMenu.getFilter().setSellerName(sellerName);
            } else if (field.equalsIgnoreCase("company")) {
                String company = checkInput("Enter company", ".+");
                ProductsMenu.getFilter().setCompany(company);
            } else if (field.equalsIgnoreCase("min stock status")) {
                int minStockStatus = Integer.parseInt(checkInput("Enter min stock status", "\\d+"));
                ProductsMenu.getFilter().setMinimumStockStatus(minStockStatus);
            } else {
                String filter = checkInput("Enter " + field, ".+");
                AuctionMenu.getFilter().getFeature().replace(field, filter);
            }
        }
    }
}
