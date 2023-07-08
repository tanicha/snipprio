package com.multiversebackend.snipprio;

import com.multiversebackend.snipprio.model.Snippet;
import com.multiversebackend.snipprio.model.User;
import com.multiversebackend.snipprio.repository.SnippetRepository;
import com.multiversebackend.snipprio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SnipprioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SnipprioApplication.class, args);
	}

	@Autowired
	private SnippetRepository snippetRepository;
	@Autowired
	private UserRepository userRepository;

	@Override //when the app first runs - this will be in our db
	public void run(String... args) throws Exception {

		//one snippet
		Snippet snippet = new Snippet();
		snippet.setId(1);
		snippet.setLanguage("python");
		snippet.setCode("print(`hello world!`)");
		snippetRepository.save(snippet);

		//2 snippet
		Snippet snippet2 = new Snippet();
		snippet2.setId(2);
		snippet2.setLanguage("python");
		snippet2.setCode("def add(a, b):\n    return a + b");
		snippetRepository.save(snippet2);

		//3 snippet
		Snippet snippet3 = new Snippet();
		snippet3.setId(3);
		snippet3.setLanguage("python");
		snippet3.setCode("class Circle:\n    def __init__(self, radius):\n        self.radius = radius\n\n    def area(self):\n        return 3.14 * self.radius ** 2");
		snippetRepository.save(snippet3);

		//4 snippet
		Snippet snippet4 = new Snippet();
		snippet4.setId(4);
		snippet4.setLanguage("javascript");
		snippet4.setCode("console.log(`hello world!`)");
		snippetRepository.save(snippet4);

		//5 snippet
		Snippet snippet5 = new Snippet();
		snippet5.setId(5);
		snippet5.setLanguage("javascript");
		snippet5.setCode("function multiply(a, b) {\n    return a * b;\n}");
		snippetRepository.save(snippet5);

		//6 snippet
		Snippet snippet6 = new Snippet();
		snippet6.setId(6);
		snippet6.setLanguage("javascript");
		snippet6.setCode("const square = num => num * num;");
		snippetRepository.save(snippet6);

		//7 snippet
		Snippet snippet7 = new Snippet();
		snippet7.setId(7);
		snippet7.setLanguage("java");
		snippet7.setCode("public class HelloWorld {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}");
		snippetRepository.save(snippet7);

		//7 snippet
		Snippet snippet8 = new Snippet();
		snippet8.setId(8);
		snippet8.setLanguage("java");
		snippet8.setCode("public class Rectangle {\n    private int width;\n    private int height;\n\n    public Rectangle(int width, int height) {\n        this.width = width;\n        this.height = height;\n    }\n\n    public int getArea() {\n        return width * height;\n    }\n}");
		snippetRepository.save(snippet8);

		//1 user - this will fail the authentication route test since the encryption pwd is already set up
		User user = new User();
		user.setId(1);
		user.setEmail("tani@example.com");
		user.setPassword("12345");
		userRepository.save(user);
	}

}

