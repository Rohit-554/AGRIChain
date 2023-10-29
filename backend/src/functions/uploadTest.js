function stringifyCircularObject(obj, seen = new Set()) {
    if (seen.has(obj)) {
      return "[Circular Reference]";
    }
  
    seen.add(obj);
  
    let result = "{ ";
    for (const key in obj) {
      if (result !== "{ ") {
        result += ", ";
      }
      if (typeof obj[key] === "object") {
        result += key + ": " + stringifyCircularObject(obj[key], seen);
      } else {
        result += key + ": " + JSON.stringify(obj[key]);
      }
    }
    result += " }";
    return result;
  }
  

module.exports = function greet(name){

// console.log(name)
// upload.single('image_url')(req, res, function (err) {
//     if (err) {
//       return res.status(400).json({ message: 'Error uploading the file' });
//     }

//     // File has been successfully uploaded
//     const uploadedFile = req.file;
//     console.log('File Name:', uploadedFile.originalname);
//     console.log('Mimetype:', uploadedFile.mimetype);

//     // You can now use the uploaded file data for further processing

//     return res.status(200).json({ message: 'File uploaded successfully' });
//   });
const circularObject = {};
circularObject.circularReference = name;

// const stringRepresentation = stringifyCircularObject(circularObject);
    return `Hello !${name}`;
}