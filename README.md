# Cross Reference Model Definition Language (CRMDL) Scheme


## CRMDL Syntax
	include:
	  <field>: <primitive type>
	  <field-alias>: <cmrdl://>
	  <field-alias>:
	    from: <cmrdl://>
	    include:
	      - <field>
	      - <field alias>
	  <field-alias>:
	    from: <cmrdl://>
	    exclude:
	      - <field>
	      - <field-alias>
	export:
	  - <field>
	  - <field-alias>


## CRMDL context

	$repo:  <domain>
	$owner: <some-body>
	$tree:  <branch> | <tag>
	$group: <some-group>
	$name:  <some>.<namespace>.<name>



# CRMDL Reference Syntax


## Local Reference

    syntax:  name#field
    example: some.namespace.name#someField
        repo    = $repo
        owner   = $owner
        group   = $group
        tree    = $tree
        name    = some.name.space.name
        field   = someField

## Cross-Tree Reference

    syntax:  tree/name#field
    example: some-tree/some.namespace.name#someField
        repo    = $repo
        owner   = $owner
        group   = $group
        tree    = some-tree
        name    = some.name.space.name
        field   = someField

## Cross-Group Reference

    syntax:  group/tree/name#field
    example: other-group/other-master/other.namespace.name#otherFiled
        repo    = $repo
        owner   = $owner
        group   = other-group
        tree    = other-master
        name    = other.namespace.name
        field   = otherFiled

## Cross-Owner Reference

    syntax:  owner/group/tree/name#field
    example: other-owner/other-group/other-master/other.namespace.name#otherFiled
        repo    = $repo
        owner   = other-owner
        group   = other-group
        tree    = other-master
        name    = other.namespace.name
        field   = otherFiled

## Cross-Repo Reference

    syntax:  /repo/owner/group/tree/name#field
    example: /other-repo/other-owner/other-group/other-master/other.namespace.name#otherFiled
        repo    = other-repo
        owner   = other-owner
        group   = other-group
        tree    = other-master
        name    = other.namespace.name
        field   = otherFiled

## Maven Mapping Reference

    syntax:   mvn://repo/owner/group/name/tree#!field
    example1: mvn://repo1.maven.org/maven2/maven-group/maven-artifactId/maven-version#!some.package.class.field
        repo    = repo1.maven.org
        owner   = maven2
        group   = maven-groupId
        tree    = maven-version
        name    = maven-artifactId
        field   = some.package.class.field
    example2: mvn://repo.spring.io/release/maven-group/maven-artifactId/maven-version#!some.package.class.field
        repo    = repo1.maven.org
        owner   = maven2
        group   = maven-groupId
        tree    = maven-version
        name    = maven-artifactId
        field   = some.package.class.field