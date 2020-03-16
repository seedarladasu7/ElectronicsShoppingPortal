package com.portal.shopping.electronics.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.portal.shopping.electronics.dto.CartProductDTO;
import com.portal.shopping.electronics.dto.ProductDTO;
import com.portal.shopping.electronics.dto.PurchaseDataDTO;
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
import com.portal.shopping.electronics.model.ProductsResponse;
import com.portal.shopping.electronics.model.PurchaseDetails;
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

	private static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public ProductsResponse getElectronicProducts(Optional<String> productName) {
		List<Product> productsList = null;

		if (productName.isPresent()) {
			productsList = productRepository.findByProductNameContainingIgnoreCaseOrderByProductName(productName.get());
		} else {
			productsList = productRepository.findAll(Sort.by("productName"));
		}

		List<ProductDTO> products = productsList.stream()
				.map(product -> new ProductDTO(product.getProductId(), product.getProductName(), product.getPrice()))
				.collect(Collectors.toList());
		return new ProductsResponse(products);
	}

	@Override
	public String addElectronicProductsToCart(ProductToCartRequest request) {

		try {
			Cart cart = new Cart();

			cart.setUpdatedOn(dateTimeFormatter.format(new java.util.Date()));

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
			
			return "Product(s) added to cart successfully...";
		} catch (Exception e) {
			throw new RecordInsertionException(e.getMessage());
		}
	}

	@Override
	public String confirmPurchase(PurchaseRequest request) {
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

			purchase.setPurchaseOn(dateTimeFormatter.format(new java.util.Date()));

			purchase.setCart(cartOpt.get());
			purchase.setPaymentMode(payModeOpt.get());
			purchase.setDeliveryMode(deliveryModeOpt.get());
			purchase.setUser(userOpt.get());
			purchaseRepository.saveAndFlush(purchase);
			return "Order placed successfully...";

		} catch (Exception e) {
			throw new RecordInsertionException(e.getMessage());
		}
	}

	@Override
	public PurchaseDetails getUserOrders(Integer userId) {

		List<Purchase> purchases = new ArrayList<>();
		Optional<User> user = userRepository.findById(userId);

		if (user.isPresent()) {
			purchases = purchaseRepository.findByUser(user.get());
		}

		PurchaseDetails purchaseDetails = new PurchaseDetails();

		if (StringUtils.isEmpty(purchaseDetails.getUserName())) {
			purchaseDetails.setUserName(purchases.get(0).getUser().getUserName());
		}
		List<PurchaseDataDTO> purchaseDataList = new ArrayList<>();
		purchases.stream().forEach(purchase -> {
			Cart cart = purchase.getCart();
			List<CartProductDTO> cartProdList = cart.getCartProducts().stream()
					.map(cartProd -> new CartProductDTO(
							productRepository.findById(cartProd.getProductId()).get().getProductName(),
							cartProd.getQuantity(), cartProd.getPrice()))
					.collect(Collectors.toList());
			
			PurchaseDataDTO pDataDTO = new PurchaseDataDTO(purchase.getPurchaseOn(),
					purchase.getPaymentMode().getPaymentModeName(), purchase.getDeliveryMode().getDeliveryModeName(), cart.getUpdatedOn(),
					cartProdList);
			purchaseDataList.add(pDataDTO);
		});

		purchaseDetails.setPurchaseData(purchaseDataList);
		return purchaseDetails;

	}

}
