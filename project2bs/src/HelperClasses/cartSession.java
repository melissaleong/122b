package HelperClasses;
import java.util.HashMap;
import java.util.ArrayList;
public class cartSession {
	private HashMap<Integer,Cart> cartItem;
	public cartSession(){
		cartItem= new HashMap<Integer, Cart>();
	}
	public Cart addtoCart(Movie movie, int quantity){
		if (cartItem.containsKey(movie.id)){
			Cart exists = cartItem.get(movie.id);
			exists.addQuantity(quantity);
			return exists;
			
		}
		else{
			Cart newItem = new Cart(movie, quantity);
			cartItem.put(movie.id, newItem);
			return newItem;
		}
	}
	public void updateCartquantity(Movie movie, int quantity){
		if(cartItem.containsKey(movie.id)){
			Cart exists = cartItem.get(movie.id);
			if(quantity==0){
				removeItem(movie);
			}
			if (quantity>=0){
			exists.setQuantity(quantity);
		}
		}
	}
	public void removeItem(Movie movie){
		if(cartItem.containsKey(movie.id)){
			cartItem.remove(movie.id);
		}
	}
	public void clearall(){
		cartItem.clear();
		cartItem =new HashMap<Integer, Cart>();
	}
	public ArrayList<Cart> returnitems(){
		return new ArrayList<Cart>(cartItem.values());
	}
}
