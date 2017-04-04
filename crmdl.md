# Cross Reference Model Definition Language (CRMDL) Scheme


## CRMDL Syntax
	include:
	  <field>: <primitive type>
	  <field-alias>: <cmrdl://>
	  <field-alias>:
	    from: <cmrdl://>
	    include:
	      - <field>
	      - <field-alias>
	  <field-alias>:
	    from: <cmrdl://>
	    exclude:
	      - <field>
	      - <field-alias>
	export:
	  - <field>
      - <field-alias>
	  - <field>?            # optional
	  - <field-alias>?      # optional


## CRMDL context

	$repo:  <domain>
	$owner: <some-body>
	$tree:  <branch> | <tag>
	$group: <some-group>
	$field:  <some>.<namespace>.<field>



# CRMDL Reference Syntax


## Local Reference

    syntax:  field#field
    example: some.namespace/field#someField
        repo    = $repo
        owner   = $owner
        group   = $group
        tree    = $tree
        field    = some.field.space.field
        field   = someField

## Cross-Tree Reference

    syntax:  tree/field#field
    example: some-tree/some.namespace/field#someField
        repo    = $repo
        owner   = $owner
        group   = $group
        tree    = some-tree
        field    = some.field.space.field
        field   = someField

## Cross-Group Reference

    syntax:  group/tree/field#field
    example: other-group/other-master/other.namespace/field#otherFiled
        repo    = $repo
        owner   = $owner
        group   = other-group
        tree    = other-master
        field    = other.namespace.field
        field   = otherFiled

## Cross-Owner Reference

    syntax:  owner/group/tree/field#field
    example: other-owner/other-group/other-master/other.namespace/field#otherFiled
        repo    = $repo
        owner   = other-owner
        group   = other-group
        tree    = other-master
        field    = other.namespace.field
        field   = otherFiled

## Cross-Repo Reference

    syntax:  /repo/owner/group/tree/field#field
    example: /other-repo/other-owner/other-group/other-master/other.namespace/field#otherFiled
        repo    = other-repo
        owner   = other-owner
        group   = other-group
        tree    = other-master
        field    = other.namespace.field
        field   = otherFiled

## Maven Mapping Reference

    syntax:   mvn://repo/owner/group/field/tree#!field
    example1: mvn://repo1.maven.org/maven2/maven-group/maven-artifactId/maven-version#!some.package.class.field
        repo    = repo1.maven.org
        owner   = maven2
        group   = maven-groupId
        tree    = maven-version
        field    = maven-artifactId
        field   = some.package.class.field
    example2: mvn://repo.spring.io/release/maven-group/maven-artifactId/maven-version#!some.package.class.field
        repo    = repo1.maven.org
        owner   = maven2
        group   = maven-groupId
        tree    = maven-version
        field    = maven-artifactId
        field   = some.package.class.field