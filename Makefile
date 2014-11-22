.PHONY: release-maven release-bintray

release-maven:
	./gradlew clean uploadArchives

release-bintray:
	./gradlew clean bintray
