package com.portal.shopping.electronics.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.portal.shopping.electronics.dto.ProductsDTO;
import com.portal.shopping.electronics.entity.Cart;
import com.portal.shopping.electronics.entity.CartProduct;
import com.portal.shopping.electronics.entity.DeliveryMode;
import com.portal.shopping.electronics.entity.PaymentMode;
import com.portal.shopping.electronics.entity.Product;
import com.portal.shopping.electronics.entity.Purchase;
import com.portal.shopping.electronics.entity.User;
import com.portal.shopping.electronics.exceptionclasses.InvalidInputException;
import com.portal.shopping.electronics.exceptionclasses.InvalidUserException;
import com.portal.shopping.electronics.exceptionclasses.RecordInsertionException;
import com.portal.shopping.electronics.model.ProductAndQuantity;
import com.portal.shopping.electronics.model.ProductToCartRequest;
import com.portal.shopping.electronics.model.PurchaseRequest;
import com.portal.shopping.electronics.repository.CartRepository;
import com.portal.shopping.electronics.repository.DeliveryModeRepository;
import com.portal.shopping.electronics.repository.PaymentModeRepository;
import com.portal.shopping.electronics.repository.ProductRepository;
import com.portal.shopping.electronics.repository.PurchaseRepository;
import com.portal.shopping.electronics.repository.UserRepository;
import com.portal.shopping.electronics.service.ElectronicsShoppingService;

@Service
public class ElectronicsShoppingServiceImpl implements ElectronicsShoppingService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	PaymentModeRepository payModeRepository;

	@Autowired
	DeliveryModeRepository deliveryModeRepository;

	@Autowired
	PurchaseRepository purchaseRepository;

	private static SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public List<ProductsDTO> getElectronicProducts(Optional<String> productName) {
		List<Product> productsList = null;

		if (productName.isPresent()) {
			productsList = productRepository.findByProductNameContainingIgnoreCaseOrderByProductName(productName.get());
		} else {
			productsList = productRepository.findAll(Sort.by("productName"));
		}

		return productsList.stream()
				.map(product -> new ProductsDTO(product.getProductId(), product.getProductName(), product.getPrice()))
				.collect(Collectors.toList());

	}

	@Override
	public void addElectronicProductsToCart(ProductToCartRequest request) {

		try {
			Cart cart = new Cart();

			cart.setUserId(request.getUserId());

			List<Integer> iProdIds = request.getProductsList().stream().map(ProductAndQuantity::getProductId)
					.collect(Collectors.toList());

			List<Product> productsList = productRepository.findByProductIdIn(iProdIds);

			Map<Integer, Double> prodPricesMap = productsList.stream()
					.collect(Collectors.toMap(Product::getProductId, Product::getPrice));

			List<ProductAndQuantity> prodAndQty = request.getProductsList();

			List<CartProduct> cartProdList = new ArrayList<>();
			prodAndQty.stream().forEach(prodQty -> {
				CartProduct cartProd = new CartProduct();
				cartProd.setPrice(prodPricesMap.get(prodQty.getProductId()) * prodQty.getQuantity());
				cartProd.setQuantity(prodQty.getQuantity());
				cartProd.setProductId(prodQty.getProductId());
				cartProdList.add(cartProd);
			});

			cart.setCartProducts(cartProdList);

			cartRepository.saveAndFlush(cart);
		} catch (Exception e) {
			throw new RecordInsertionException(e.getMessage());
		}
	}

	@Override
	public void confirmPurchase(PurchaseRequest request) {
		try {

			Optional<User> userOpt = userRepository.findById(request.getUserId());

			if (!userOpt.isPresent()) {
				throw new InvalidUserException("Invalid User");
			}

			Optional<Cart> cartOpt = cartRepository.findById(request.getCartId());

			Optional<PaymentMode> payModeOpt = payModeRepository.findById(request.getPaymentModeId());

			Optional<DeliveryMode> deliveryModeOpt = deliveryModeRepository.findById(request.getDeliveryModeId());

			if (!cartOpt.isPresent() || !payModeOpt.isPresent() || !deliveryModeOpt.isPresent()) {
				throw new InvalidInputException("Invalid Cart Information");
			}

			Purchase purchase = new Purchase();

			purchase.setPurchaseOn(dateTime.format(new java.util.Date()));

			purchase.setCartId(cartOpt.get().getCartId());
			purchase.setPaymentModeId(payModeOpt.get().getPaymentModeId());
			purchase.setDeliveryModeId(deliveryModeOpt.get().getDeliveryModeId());
			purchase.setUser(userOpt.get());
			purchaseRepository.saveAndFlush(purchase);

		} catch (Exception e) {
			throw new RecordInsertionException(e.getMessage());
		}
	}
	
	@Override
	public List<Purchase> getUserOrders(Integer userId) {
		
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			purchaseRepository.findByUser(user.get());
		}

		return new ArrayList<>();

	}

}
