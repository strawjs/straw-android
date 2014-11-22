.PHONY: release

release:
	./gradlew clean uploadArchives
