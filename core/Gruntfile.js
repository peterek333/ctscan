var hash = Math.floor((Math.random() * 100000000) + 1).toString();

module.exports = function(grunt) {

    grunt.initConfig({
        pkg : grunt.file.readJSON('package.json'),

        clean : {
            build : {
                src : [ 'min/*' ]
            },
            temp : {
                src : [ 'min/temp/' ]
            },
            develop : {
                options : {
                    force : true
                },
                src : [ '../../../../target/classes/static/min/*' ]
            }
        },
        sass: {
            local: {
                cwd: 'styles/',
                src: [ '*.scss' ],
                dest: 'styles/css/',
                ext: '.css',
                expand: true
            }
        },
        concat : {
            local : {
                files : {
                    'min/temp/styles.css' : [ 'styles/css/*.css' ],
                    'min/temp/controllers.js' : [ 'app.js', 'modules/**/*.js' ],
                    'min/temp/vendor.css' : [
                        'bower_components/bootstrap/dist/css/bootstrap.css',
                        'bower_components/bootstrap/dist/css/bootstrap-theme.css'
                    ],
                    'min/temp/vendor.js' : [
                        'bower_components/jquery/dist/jquery.js',
                        'bower_components/angular/angular.js',
                        'bower_components/bootstrap/dist/js/bootstrap.js',
                        'bower_components/angular-ui-router/release/angular-ui-router.js',
                        'bower_components/angular-animate/angular-animate.js',
                        'bower_components/a0-angular-storage/dist/angular-storage.js',
                        'bower_components/jwt-decode/build/jwt-decode.js'
                    ]
                }
            }
        },
        uglify : {
            local : {
                files : {
                    'min/temp/controllers.js' : [ 'min/temp/controllers.js' ]
                },
                options : {
                    mangle : false
                }
            },
            bower_components : {
                files : {
                    'min/temp/vendor.js' : [ 'min/temp/vendor.js' ]
                },
                options : {
                    mangle : false
                }
            }
        },
        cssmin : {
            css : {
                src : 'min/temp/styles-vna.css',
                dest : 'min/temp/styles-vna.css'
            },
            bower_components : {
                src : 'min/temp/vendor.css',
                dest : 'min/temp/vendor.css'
            }
        },
        replace : {
            local : {
                options : {
                    variables : { 'hash' : hash }
                },
                files : { 'index.html' : [ 'index.tpl.html' ] }
            }
        },
        hash : {
            options : {
                mapping: '', //mapping file so your server can serve the right files
                hashFunction : function(source, encoding) {
                    return hash;
                }
            },
            js : {
                src : 'min/temp/*.js',
                dest : 'min/'
            },
            css : {
                src : 'min/temp/*.css',
                dest : 'min/'
            }
        },
        copy : {
            develop : {
                cwd : '.',
                src : ['**/*'],
                dest : '../../../../target/classes/static',
                expand : true
            },
            forWatch : {
                cwd : '.',
                src : ['**/*', '!**/bower_components/**', '!**/node_modules/**'],
                dest : '../../../../target/classes/static',
                expand : true
            }
        },
        watch : {
            dev : {
                files : [ 'index.tpl.html', 'app.js',  'modules/**/*',
                    'styles/*.scss', 'version/*.js', 'languages/*'],
                tasks : [ 'forWatch' ],
                options : {
                    atBegin : true
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-replace');
    grunt.loadNpmTasks('grunt-hash');
    grunt.loadNpmTasks('grunt-sass');


    grunt.file.setBase(require('./bower.json').appPath || 'src/main/resources/static');

    grunt.registerTask('default', [ 'clean:build', 'replace', 'sass', 'concat', 'uglify', 'cssmin', 'hash',  'clean:temp', 'copy:fonts', 'copy:develop', 'copy:fontAwesome' ]);
    grunt.registerTask('forWatch', [ 'clean:build', 'replace', 'sass', 'concat', 'hash', 'clean:temp', 'clean:develop', 'copy:forWatch' ]);
};