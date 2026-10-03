# A simple Apache mod_autoindex browser

This is an example of using Apache [mod_autoindex] feature and a static home page to provide a simple development artifact repository.

### Apache directives to config - mod_autoindex
Prerequisite:
- the apache module [mod_autoindex] must be installed and enabled in the apache server configuration.
- then you can put a directives config to the domain host you want to use (e.g. mydomain.org) to config look and styling etc.
```xml
<Location />
	Options +Indexes
	IndexStyleSheet "/styles/apache-repo-browser.css"
	ReadmeName "/apache-repo-browser-footer.html"
	DefaultIcon "/assets/icons/empty.svg"
	AddIcon "/assets/icons/folder.svg" ^^DIRECTORY^^
	AddIcon "/assets/icons/dir-up.svg" ..
	AddIcon "/assets/icons/empty.svg" .zip
</Location>
```   
