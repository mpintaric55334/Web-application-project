function noticeCategory(category) {
  if(!category) {
    return "Sve kategorije";
  }

  switch (category) {
    case "LABORATORIJSKA_VJEZBA":
      return "Laboratorijska vjezba"

    case "KONTINUIRANI_ISPIT":
      return "Kontinuirani ispit"

    case "ISPITNI_ROK":
      return "Ispitni rok"

    case "BLIC":
      return "Blic"

    case "GRADIVO":
      return "Gradivo"

    default:
      return null;
  }

}

export default noticeCategory;
