'use-strict';

const gulp = require('gulp'),
    gutil = require('gulp-util'),
    config = require('./config');

gulp.task('default', function() {
    gutil.log(config.paths.app);
    gutil.log(config.paths.assets);
    gutil.log(config.paths.build);
});