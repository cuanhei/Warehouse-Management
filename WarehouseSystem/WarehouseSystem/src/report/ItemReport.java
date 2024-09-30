package report;

import java.util.Arrays;

import document.*;
import item.Item;

public class ItemReport extends Report {
	
	private Item[] top10Item;
	private int[] purchasedQty;
    private static final String TITLE = "TOP 10 SELLING ITEMS";
    

    public ItemReport(Item[] items, Document[] documents) {
        super(TITLE);
        this.top10Item = items;
        this.purchasedQty = new int[top10Item.length];
        getAllQtyFromInv(documents);
    }


	private void getAllQtyFromInv(Document[] documents) {
		for(Document doc : documents) {
			if(doc == null) break;
			if(doc instanceof Invoice) {
				Item[] invItems = ((Invoice)doc).getOrder().getItems();
				int[] invItemsQty = ((Invoice)doc).getOrder().getQuantity();
				for(int i = 0; i < invItems.length; i++) {
					for(int ii = 0; ii < top10Item.length; ii++) {
						if(invItems[i]!= null) {
							if(invItems[i].equals(top10Item[ii])){
								purchasedQty[ii] += invItemsQty[i];
								break;
							}
						}
					}
				}
			}
		}
	}
	
    
    // Instance method to print a vertical bar chart in the console
    @Override
    public void drawReport() {
        int n = purchasedQty.length;

        // Create an array of indices to represent the items
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        // Sort the indices based on the corresponding quantities in descending order
        Arrays.sort(indices, (i1, i2) -> Integer.compare(purchasedQty[i2], purchasedQty[i1]));

        // Determine the maximum quantity for chart height scaling
        int maxQuantity = Arrays.stream(purchasedQty).max().orElse(1);
        int maxHeight = maxQuantity / 100;  // Scaling factor (1 '#' = 100 units)

        System.out.print(super.toString());
        System.out.println(" [\"\u001B[32m#\u001B[0m\" represents 100]\n\n");
        // Print the chart row by row, from top to bottom
        for (int i = maxHeight; i > 0; i--) {
            for (int j = 0; j < Math.min(10, n); j++) { // Change here to 10 instead of 100
                if (purchasedQty[indices[j]] / 100 >= i) {
                    System.out.print("   #   ");  // Print a '#' if the bar height is at this level
                } else {
                    System.out.print("       ");  // Print empty space otherwise
                }
            }
            System.out.println();  // New row
        }

        // Print the x-axis (Item IDs)
        for (int i = 0; i < Math.min(10, n); i++) {
            System.out.printf("  ID%-3d", top10Item[indices[i]].getId());
        }
        System.out.println();

        // Print the quantities below the x-axis
        for (int i = 0; i < Math.min(10, n); i++) {
            System.out.printf(" (%-3d) ", purchasedQty[indices[i]]);
        }
        System.out.println();
    }

    @Override
	public String toString() {
		return super.toString();
	}
    @Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}

 
