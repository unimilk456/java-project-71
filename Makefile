.DEFAULT_GOAL := build-run

run-dist:
    make -C app run-dist

setup:
    make -C app setup

clean:
    make -C app clean

build:
    make -C app build

install:
	 make -C app install

run:
	make -C app run

test:
	make -C app test

report:
	make -C app report

lint:
    make -C app lint

checkstyleMain:
    ./gradlew checkstyleMain


check-deps:
    make -C app check-deps

build-run: build run

.PHONY: build