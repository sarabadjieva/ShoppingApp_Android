# ShoppingApp_Android

Project for course CITB_704

The shopping app is created in Android Studio using the programming language Java.

The project contains three activities - main, home and for payment. The main activity is used as starting screen and when the data is loaded
from the database and from a json file (a github gist), the program proceeds to load the home screen. The database is loaded on a new thread and the data from the API 
is loaded via Async Task. The bootom navigation is used to swap between favorite items, cart and homepage.
The homepage contains a tab layout with two tabs - inspiration and the different categories. When a category button is clicked a new fragment with the items in this category
is loaded. The player can add to favorites and to cart from there. The images are cached as bitmaps in order to optimize the downloading. The data for favorites and cart is 
stored in the database under two tables. When the player clicks on "pay" in the cart a dialog window opens. If the player chooses the positive answer a new fragment for the payment
is loaded (should be implemented).

The item cells in the category view, cart and favorites are implemented in RecyclerView. There are two different .xml files for them "item_cell" and "item_cell_long", the first 
one is only used in the single category view. Items in favorites and cart can be removed and the view is updated.
