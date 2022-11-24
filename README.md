## Warehouse Implementation

This is a warehouse software implementation which holds **products** and **articles**. Each article contains an identification number, a name and available stock; whereas products are made of different articles. Products have a name, price and a list of articles of which they are made from with a quantity.

### Functionality

Current warehouse implementation supports the following functionality:

- List all products and quantities of each that is an available with the current inventory.
- Sell a product and update the inventory and products accordingly.

### Data Source

The warehouse uses MongoDB and includes 2 documents as products and inventory.

See the [resources](https://github.com/aylingumus/warehouse-impl/tree/master/src/main/resources) for more information.

## API

### Endpoints

- (**GET**) `/warehouse/available-products`: returns the available products
- (**PUT**) `/warehouse/sell-product`: sells a product and updates the inventory and products if needed

## License

Licensed under [The MIT License](http://opensource.org/licenses/MIT).

## Copyright

Copyright © 2022, [Aylin Gumus](mailto:aylingumus7@gmail.com)