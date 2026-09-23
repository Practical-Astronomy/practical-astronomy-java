ifeq ($(OS),Windows_NT)
    GRADLEW := .\gradlew.bat
	RMDIR := rmdir /S /Q
else
    GRADLEW := ./gradlew
	RMDIR := rm -rf
endif

default:
	@echo 'Targets:'
	@echo '  gradle-build  -- build via Gradle wrapper'
	@echo '  gradle-test   -- run unit tests'
	@echo '  gradle-tasks  -- show Gradle tasks available in this project'
	@echo '  docs          -- generate documentation (requires Doxygen)'
	@echo '  clean         -- remove build artifacts, delete docs directory'

gradle-build:
	$(GRADLEW) build

gradle-test:
	$(GRADLEW) test

gradle-tasks:
	$(GRADLEW) tasks

docs: clean
	doxygen

clean:
	$(GRADLEW) clean
	$(RMDIR) docs