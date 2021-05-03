# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.0.2] - 2021-05-03
### Added
- Launcher for Windows

## [1.0.1] - 2021-05-02
### Added
- toBinaryString(String str) method required for represent committed files in binary form
- getConfigData() method which return data from config file
- Token in PathAndTokens class which flagging committer information

### Changed
- Now committed files represented in binary form in commit files

### Fixed
- Critical error when enter command which has optional argument without argument

## [1.0.0] - 2021-05-01
### Added
- Base commands and implementation of version control 
