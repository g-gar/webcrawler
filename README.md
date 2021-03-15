# webcrawler

## Requirements
Clone and install https://github.com/g-gar/robotstxt-java in your local maven repository 

## Features
* [Robots Exclusion Protocol](https://en.wikipedia.org/wiki/Robots_exclusion_standard) (i.e. robots.txt) support
* URLs blacklisting support based on [regular expressions](https://en.wikipedia.org/wiki/Regular_expression)

## Examples
Can be found under [webcrawler/tests](https://github.com/g-gar/webcrawler/tree/main/tests) submodule:
* [Basic example using Jsoup](https://github.com/g-gar/webcrawler/blob/main/tests/test1/src/main/java/com/ggar/webcrawler/tests/test1/Entrypoint.java)
* [Basic example using Playwright](https://github.com/g-gar/webcrawler/blob/main/tests/test2/src/main/java/com/ggar/webcrawler/tests/test2/Entrypoint.java)