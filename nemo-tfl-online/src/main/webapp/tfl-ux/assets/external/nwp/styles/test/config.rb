#STANDARD RUBY CONFIG FILES SETTINGS FOR COMMAND LINE COMPASS WATCH
environment = :development
firesass = false

css_dir         = "core/min"
sass_dir        = "core"
extensions_dir  = ""
images_dir      = ""
javascripts_dir = ""

output_style = :compressed

relative_assets = true


#PREPROS SETTINGS FOR MUTIPLE OUTPUTS
require 'fileutils'

on_stylesheet_saved do |file|
  if File.exists?(file)
    filename = File.basename(file, File.extname(file))
    File.rename(file, "core/min/" + filename + ".min" + File.extname(file))
  end
end