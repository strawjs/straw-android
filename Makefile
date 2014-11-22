.PHONY: release-maven release-bintray

release-maven:
	./gradlew clean uploadArchives

release-maven:
	./gradlew clean bintray
