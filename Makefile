.PHONY: test

test: node_modules

node_modules: package.json
	npm install
	@touch node_modules

clean:
	rm -rf out/

distclean: clean
	rm -rf node_modules

build:
	clojure -A:build -m hf.depstar.jar tailwind-hiccup-multiline-classes.jar

deploy:
	clojure -A:build -m deps-deploy.deps-deploy deploy tailwind-hiccup-multiline-classes.jar.jar true