require "json"


Pod::Spec.new do |s|
  package = JSON.parse(File.read(File.join(__dir__, "package.json")))
  s.name         = "react-native-floating-bubble-plugin"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = "https://github.com/antondev-123/react-native-floating-bubble-plugin"
  s.source       = { :git => "https://github.com/antondev-123/react-native-floating-bubble-plugin", :tag => "#{s.version}" }
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => "11.0" } # Set a minimum iOS version, even if unsupported

  s.source_files = "ios/**/*.{h,m,swift}"

  s.dependency "React-Core"
end
