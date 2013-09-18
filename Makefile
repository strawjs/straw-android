
.PHONY: release
release:
	git clone git@github.com:kt3k/repository.git
	mvn deploy -Dinternal.repo.path=file://`pwd`/repository/maven/release
	cd repository ; git add . ; git commit -m "Automated release" ; git checkout gh-pages ; git merge master ; git push origin master gh-pages
	rm -rf repository
