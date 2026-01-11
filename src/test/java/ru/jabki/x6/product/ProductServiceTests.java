package ru.jabki.x6.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import ru.jabki.x6.product.model.Product;
import ru.jabki.x6.product.repository.ProductRepository;
import ru.jabki.x6.product.service.ProductService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@Test
	void createProduct_valid() {
		final Product product = getProduct();
		when(productRepository.insert(product)).thenReturn(product);
		Product result = productService.create(product);
		assertThat(result).isEqualTo(product);
		verify(productRepository).insert(product);
	}

	@Test
	void getProduct_valid() {
		final Product product = getProduct();
		when(productRepository.getById(product.getId())).thenReturn(product);
		Product result = productService.getById(product.getId());
		assertThat(result).isEqualTo(product);
		verify(productRepository).getById(product.getId());
	}

	@Test
	void deleteProduct_valid() {
		long id = 1L;
		doNothing().when(productRepository).delete(id);
		productService.delete(id);
		verify(productRepository).delete(id);
	}

	private Product getProduct() {
		return Product.builder()
				.id(1L)
				.name("Some name")
				.description("Some description")
				.price(100F)
				.category("some category")
				.build();
	}

}
